package com.ithelpdesk.backend.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponse {

    private String employeeName;

    private Long totalTickets;

    private Long openTickets;

    private Long inProgressTickets;

    private Long resolvedTickets;

    private Long closedTickets;

    private List<TicketResponse> recentTickets;

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Long getTotalTickets() {
		return totalTickets;
	}

	public void setTotalTickets(Long totalTickets) {
		this.totalTickets = totalTickets;
	}

	public Long getOpenTickets() {
		return openTickets;
	}

	public void setOpenTickets(Long openTickets) {
		this.openTickets = openTickets;
	}

	public Long getInProgressTickets() {
		return inProgressTickets;
	}

	public void setInProgressTickets(Long inProgressTickets) {
		this.inProgressTickets = inProgressTickets;
	}

	public Long getResolvedTickets() {
		return resolvedTickets;
	}

	public void setResolvedTickets(Long resolvedTickets) {
		this.resolvedTickets = resolvedTickets;
	}

	public Long getClosedTickets() {
		return closedTickets;
	}

	public void setClosedTickets(Long closedTickets) {
		this.closedTickets = closedTickets;
	}

	public List<TicketResponse> getRecentTickets() {
		return recentTickets;
	}

	public void setRecentTickets(List<TicketResponse> recentTickets) {
		this.recentTickets = recentTickets;
	}

}