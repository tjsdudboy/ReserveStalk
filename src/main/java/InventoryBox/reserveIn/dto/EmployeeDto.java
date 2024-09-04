package InventoryBox.reserveIn.dto;

import InventoryBox.reserveIn.entity.Employee;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class EmployeeDto {
    private Long id;
    private String staffName;
    private String staffNum;
    private String nickName;
    private String staffPhone;
    private String hireDate;
    private String StaffRole;
    private String company;
    private LocalDateTime creatDate;
    private LocalDateTime updateDate;

    public static EmployeeDto toDto(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .staffName(employee.getStaffName())
                .staffNum(employee.getStaffNum())
                .nickName(employee.getNickName())
                .staffPhone(employee.getStaffPhone())
                .hireDate(employee.getHireDate())
                .StaffRole(employee.getStaffRole())
                .creatDate(employee.getCreateAt())
                .updateDate(employee.getUpdateAt())
                .company(employee.getUsers().getName())
                .build();
    }
}
