package DTOs;

import java.time.Instant;

public class AppointmentDTO {
    private Long customerId;
    private Integer branchId;
    private Integer makeupArtistId;
    private Integer serviceId;
    private Instant appointmentDate;
    private String status;

    // Getters and Setters
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getMakeupArtistId() {
        return makeupArtistId;
    }

    public void setMakeupArtistId(Integer makeupArtistId) {
        this.makeupArtistId = makeupArtistId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Instant getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Instant appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}