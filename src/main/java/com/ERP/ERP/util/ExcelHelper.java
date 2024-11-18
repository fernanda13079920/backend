package com.ERP.ERP.util;

import com.ERP.ERP.model.Paciente;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ExcelHelper {

    public static List<Paciente> excelToPacientes(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet sheet = workbook.getSheetAt(0);
            List<Paciente> pacientes = new ArrayList<>();

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue; // Omitir encabezado
                Paciente paciente = new Paciente();

                paciente.setCi(row.getCell(0).getStringCellValue());
                paciente.setNombre(row.getCell(1).getStringCellValue());
                paciente.setApellidoPaterno(row.getCell(2).getStringCellValue());
                paciente.setApellidoMaterno(row.getCell(3).getStringCellValue());
                paciente.setFechaNacimiento(row.getCell(4).getDateCellValue());
                paciente.setGenero(row.getCell(5).getStringCellValue());
                paciente.setDireccion(row.getCell(6).getStringCellValue());
                paciente.setTelefono(row.getCell(7).getStringCellValue());
                paciente.setEmail(row.getCell(8).getStringCellValue());
                paciente.setEstado(true); // Por defecto activo

                pacientes.add(paciente);
            }

            workbook.close();
            return pacientes;
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el archivo Excel: " + e.getMessage());
        }
    }
}
