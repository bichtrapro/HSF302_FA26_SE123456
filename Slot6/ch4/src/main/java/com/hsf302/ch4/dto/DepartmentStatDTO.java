package com.hsf302.ch4.dto;


    //TODO 14
public record DepartmentStatDTO(String code, String name, Long totalStudents, Double avgGpa) { //Thứ tự tham số phải khớp với SELECT

        @Override
        public String toString() {
            return String.format("%-3s | %-25s | %2d | %s",
                    code, name, totalStudents, avgGpa == null ? "null" : String.format("%.3f", avgGpa));
        }

}
