package mx.edu.uaz.vistas;

import com.vaadin.addon.spreadsheet.Spreadsheet;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.*;
import com.vaadin.shared.ui.colorpicker.Color;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import mx.edu.uaz.accesodatos.ADActividad;
import mx.edu.uaz.accesodatos.ADEje;
import mx.edu.uaz.accesodatos.ADIndicador;
import mx.edu.uaz.accesodatos.ADMeta;
import mx.edu.uaz.modelos.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class SpreadsheetReport extends CssLayout {

    private Button btnDownload;
    private int START_COLUMN = 0;
    private int START_ROW = 1;
    private int SEPARATION = 3;
    private int MAX_COLUMNS = 100;
    private int MAX_ROWS = 100;
    private Spreadsheet spreadsheet = null;

    public SpreadsheetReport(Usuario usuario){
        btnDownload = new Button("Descargar", VaadinIcons.DOWNLOAD);
        btnDownload.setStyleName(ValoTheme.BUTTON_PRIMARY);
        btnDownload.addStyleName("dwn-btn");

        setId("spreadsheetreport");

        setSizeFull();

        crearReporte();

        File file = null;
        try {
            file = spreadsheet.write(VaadinService.getCurrent().getBaseDirectory().getAbsolutePath()+ "/reports/report.xlsx");
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileResource res = new FileResource(file);

        FileDownloader fd = new FileDownloader(res);
        fd.extend(btnDownload);


        addComponent(btnDownload);

    }

    public void crearReporte() {
        if (spreadsheet != null) {
            removeComponent(spreadsheet);
        }

        spreadsheet = new Spreadsheet();
        spreadsheet.setHeight("500px");
        spreadsheet.setWidth("100%");
        spreadsheet.setResponsive(true);

        ADEje adEje = new ADEje();
        ADMeta adMeta = new ADMeta();
        ADIndicador adIndicador = new ADIndicador();
        ADActividad adActividad = new ADActividad();

        SimpleDateFormat dt = new SimpleDateFormat("dd - MM - yyyy");

        for (Eje e : adEje.obtenEjes()) {
            try {
                spreadsheet.createNewSheet(e.getNombre().length() < 30 ? e.getNombre().substring(0, e.getNombre().length()) : e.getNombre().substring(0, 30), MAX_ROWS, MAX_COLUMNS);
            } catch (Exception ex) {
                Notification.show("Error, ejes con nombres iguales.", Notification.Type.ERROR_MESSAGE);
                return;
            }
        }


        ArrayList<Cell> headerCells = new ArrayList<>();
        ArrayList<Cell> contentCells = new ArrayList<>();

        int index = 1;
        for (Eje e : adEje.obtenEjes()) {
            spreadsheet.setActiveSheetIndex(index);
            spreadsheet.getActiveSheet().protectSheet("");

            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN, "EJES ESTRATÉGICOS"));
            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN + SEPARATION, "METAS"));
            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN + SEPARATION * 2, "INDICADORES"));
            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN + SEPARATION * 3, "ACTIVIDADES"));
            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN + SEPARATION * 4, "FECHA DE INICIO DE LA ACTIVIDAD"));
            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN + SEPARATION * 5, "FECHA DE FIN DE LA ACTIVIDAD"));
            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN + SEPARATION * 6, "RESPONSABLE"));
            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN + SEPARATION * 7, "COMISIÓN"));
            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN + SEPARATION * 8, "RECURSOS PLANEADO"));
            headerCells.add(spreadsheet.createCell(START_ROW - 1, START_COLUMN + SEPARATION * 9, "ORIGEN DEL RECURSO"));

            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN, START_ROW - 1, START_COLUMN + SEPARATION - 1);
            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN + SEPARATION, START_ROW - 1, START_COLUMN + SEPARATION * 2 - 1);
            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN + SEPARATION * 2, START_ROW - 1, START_COLUMN + SEPARATION * 3 - 1);
            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN + SEPARATION * 3, START_ROW - 1, START_COLUMN + SEPARATION * 4 - 1);
            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN + SEPARATION * 4, START_ROW - 1, START_COLUMN + SEPARATION * 5 - 1);
            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN + SEPARATION * 5, START_ROW - 1, START_COLUMN + SEPARATION * 6 - 1);
            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN + SEPARATION * 6, START_ROW - 1, START_COLUMN + SEPARATION * 7 - 1);
            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN + SEPARATION * 7, START_ROW - 1, START_COLUMN + SEPARATION * 8 - 1);
            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN + SEPARATION * 8, START_ROW - 1, START_COLUMN + SEPARATION * 9 - 1);
            spreadsheet.addMergedRegion(START_ROW - 1, START_COLUMN + SEPARATION * 9, START_ROW - 1, START_COLUMN + SEPARATION * 10 - 1);

            contentCells.add(spreadsheet.createCell(START_ROW, START_COLUMN, e.getNombre()));
            int i = 0;
            int j = 0;
            for (Meta m : adMeta.obtenMetasByIDEje(e.getId_eje())) {
                contentCells.add(spreadsheet.createCell(START_ROW + i, START_COLUMN + SEPARATION, m.getNombre()));
                if(adIndicador.obtenIndicadoresByidMeta(m.getId_meta()).size() == 0) {
                    i++;
                }
                int k = 0;
                for (Indicador in : adIndicador.obtenIndicadoresByidMeta(m.getId_meta())) {
                    contentCells.add(spreadsheet.createCell(START_ROW + i, START_COLUMN + SEPARATION * 2, in.getNombre()));

                    int l = 0;
                    if(adActividad.obtenActividadesByIndicador(in.getId_Indicador()).size() == 0) {
                        i++;
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 3, START_ROW + i - 1, START_COLUMN + SEPARATION * 4 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 4, START_ROW + i - 1, START_COLUMN + SEPARATION * 5 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 5, START_ROW + i - 1, START_COLUMN + SEPARATION * 6 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 6, START_ROW + i - 1, START_COLUMN + SEPARATION * 7 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 7, START_ROW + i - 1, START_COLUMN + SEPARATION * 8 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 8, START_ROW + i - 1, START_COLUMN + SEPARATION * 9 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 9, START_ROW + i - 1, START_COLUMN + SEPARATION * 10 - 1);
                        l = i  - j - k;
                    }
                    for (Actividad a : adActividad.obtenActividadesByIndicador(in.getId_Indicador())) {
                        contentCells.add(spreadsheet.createCell(START_ROW + i, START_COLUMN + SEPARATION * 3, a.getNombre()));
                        contentCells.add(spreadsheet.createCell(START_ROW + i, START_COLUMN + SEPARATION * 4, dt.format(a.getFecha_inicio())));
                        contentCells.add(spreadsheet.createCell(START_ROW + i, START_COLUMN + SEPARATION * 5, dt.format(a.getFecha_final())));
                        contentCells.add(spreadsheet.createCell(START_ROW + i, START_COLUMN + SEPARATION * 6, a.getId_usuario().getNombre() + " " + a.getId_usuario().getApellidos()));
                        contentCells.add(spreadsheet.createCell(START_ROW + i, START_COLUMN + SEPARATION * 7, a.getId_usuario().getNombre_comisionado()));
                        contentCells.add(spreadsheet.createCell(START_ROW + i, START_COLUMN + SEPARATION * 8, "$" + a.getRecurso()));
                        contentCells.add(spreadsheet.createCell(START_ROW + i, START_COLUMN + SEPARATION * 9, a.getId_origenRecurso().getNombre_recurso()));
                        i++;
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 3, START_ROW + i - 1, START_COLUMN + SEPARATION * 4 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 4, START_ROW + i - 1, START_COLUMN + SEPARATION * 5 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 5, START_ROW + i - 1, START_COLUMN + SEPARATION * 6 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 6, START_ROW + i - 1, START_COLUMN + SEPARATION * 7 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 7, START_ROW + i - 1, START_COLUMN + SEPARATION * 8 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 8, START_ROW + i - 1, START_COLUMN + SEPARATION * 9 - 1);
                        spreadsheet.addMergedRegion(START_ROW + l + k + j, START_COLUMN + SEPARATION * 9, START_ROW + i - 1, START_COLUMN + SEPARATION * 10 - 1);
                        l = i  - j - k;
                    }
                    spreadsheet.addMergedRegion(START_ROW + k + j, START_COLUMN + SEPARATION * 2, START_ROW + i - 1,START_COLUMN + SEPARATION * 3 - 1);
                    k = i - j;
                }

                spreadsheet.addMergedRegion(START_ROW + j, START_COLUMN + SEPARATION, START_ROW + i - 1, START_COLUMN + SEPARATION * 2 - 1);
                j = i;
            }

            if(i > 1) {
                spreadsheet.addMergedRegion(START_ROW, START_COLUMN, START_ROW + i - 1, START_COLUMN + SEPARATION - 1);
            } else {
                spreadsheet.addMergedRegion(START_ROW, START_COLUMN, START_ROW + SEPARATION, START_COLUMN + SEPARATION - 1);
            }

            for(int cont = START_ROW - 1; cont < START_ROW + i; cont++){
                spreadsheet.setRowHeight(cont, 50);
            }


            index++;
        }

        XSSFColor borderColor = new XSSFColor(java.awt.Color.black);

        XSSFCellStyle headerStyle = (XSSFCellStyle) spreadsheet.getWorkbook().createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setWrapText(true);

        XSSFColor color = new XSSFColor(java.awt.Color.decode(Color.CYAN.getCSS()));
        headerStyle.setFillBackgroundColor(color);
        headerStyle.setFillForegroundColor(color);
        Font boldFont = spreadsheet.getWorkbook().createFont();
        boldFont.setBold(true);
        boldFont.setColor(HSSFColor.BLACK.index);
        headerStyle.setFont(boldFont);

        XSSFCellStyle contentStyle = (XSSFCellStyle) spreadsheet.getWorkbook().createCellStyle();
        contentStyle.setAlignment(HorizontalAlignment.CENTER);
        contentStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentStyle.setWrapText(true);

        for(Cell c : headerCells) {
            c.setCellStyle(headerStyle);
        }
        spreadsheet.refreshCells(headerCells);


        for(Cell c : contentCells) {
            c.setCellStyle(contentStyle);
        }
        spreadsheet.refreshCells(contentCells);


        spreadsheet.deleteSheet(0);
        spreadsheet.setActiveSheetIndex(0);

        spreadsheet.setFunctionBarVisible(false);
        spreadsheet.setRowColHeadingsVisible(false);
        spreadsheet.setResponsive(true);

        addComponent(spreadsheet);
    }

}
