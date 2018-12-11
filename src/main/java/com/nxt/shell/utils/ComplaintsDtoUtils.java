package com.nxt.shell.utils;

import com.nxt.shell.dto.ComplaintsDto;
import com.nxt.shell.dto.ComplaintsDto.ComplaintsDtoBuilder;
import com.nxt.shell.dto.ComplaintsOrganizationDto;
import com.nxt.shell.model.ComplaintsOrganization;

import java.util.*;

public interface ComplaintsDtoUtils {

    static ComplaintsBuilder builder(int initialCapacity){
        return new ComplaintsBuilder(initialCapacity);
    }

    class ComplaintsBuilder{

        private final List<ComplaintsDto> complaintsDtos;

         ComplaintsBuilder(int initialCapacity){
            this.complaintsDtos = new ArrayList<>(initialCapacity);
        }


        private  ComplaintsDto parseToDto(ComplaintsDtoBuilder builder, ComplaintsOrganization organization) {
            return builder.orgName(organization.getOrgName())
                    .orgAddress(organization.getOrgAddress())
                    .email(organization.getEmail())
                    .fax(organization.getFax())
                    .phone(organization.getPhone())
                    .mobilePhone(organization.getCellphone())
                    .email(organization.getEmail())
                    .build();
        }

        public  ComplaintsBuilder parseAreaToDto(ComplaintsOrganization organization) {
            return parseAreaToDto(organization,organization.getAreaManage().getName(),"","");
        }

        public  ComplaintsBuilder parseAreaToDto(ComplaintsOrganization organization,String provinceName) {
            return parseAreaToDto(organization,provinceName,"","");
        }

        public  ComplaintsBuilder parseAreaToDto(ComplaintsOrganization organization,String provinceName,String cityName) {
            return parseAreaToDto(organization,provinceName,cityName,"");
        }

        public  ComplaintsBuilder parseAreaToDto(ComplaintsOrganization organization,String provinceName,String cityName,String countyName) {
            ComplaintsDtoBuilder builder = ComplaintsDto
                    .builder()
                    .provinceName(provinceName)
                    .cityName(cityName)
                    .countyName(countyName);
            this.parseToDto(builder,organization);
            Optional.ofNullable(builder.build()).ifPresent(complaintsDtos::add);
            return this;
        }


        public  ComplaintsBuilder parseAreaToDto(ComplaintsOrganizationDto organization, String provinceName, String cityName, String countyName) {
            ComplaintsDtoBuilder builder = ComplaintsDto
                    .builder()
                    .provinceName(provinceName)
                    .cityName(cityName)
                    .countyName(countyName);
            this.parseToDto(builder,convertDto(organization));
            Optional.ofNullable(builder.build()).ifPresent(complaintsDtos::add);
            return this;
        }


        private ComplaintsOrganization convertDto(ComplaintsOrganizationDto organization){
           return ComplaintsOrganization.builder().orgName(organization.getOrgName())
                    .orgAddress(organization.getOrgAddress())
                    .email(organization.getEmail())
                    .fax(organization.getFax())
                    .phone(organization.getPhone())
                    .cellphone(organization.getCellphone())
                    .email(organization.getEmail())
                    .build();
        }
        public List<ComplaintsDto> buildDtoCollection() {
            return Collections.unmodifiableList(complaintsDtos);
        }

    }


}
