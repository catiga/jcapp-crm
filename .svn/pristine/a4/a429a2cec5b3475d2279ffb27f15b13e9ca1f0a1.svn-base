package com.jeancoder.crm.ready.dto.order

import java.util.List
class MembersBuyPage {
	boolean available;
	String msg;
	Integer pn;
	Integer ps;
	
	List<MembersBuyDto> result = new ArrayList<MembersBuyDto>();
	
	long totalCount = -1;
	
	public int getPn() {
		return pn;
	}
	public void setPn(final Integer pn) {
		this.pn = pn;

		if (pn==null || pn<1) {
			this.pn = 1;
		}
	}
	
	public int getPs() {
		return ps;
	}
	public void setPs(final Integer ps) {
		this.ps = ps;
	}
	
	public List<MembersBuyDto> getResult() {
		return result;
	}
	public void setResult(final List<MembersBuyDto> result) {
		this.result = result;
	}
	
	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(final long totalCount) {
		this.totalCount = totalCount;
	}
	
	public long getTotalPages() {
		if (totalCount < 0) {
			return -1;
		}

		long count = totalCount / ps;
		if (totalCount % ps > 0) {
			count++;
		}
		return count;
	}
	
	public boolean isNext() {
		return (pn + 1 <= getTotalPages());
	}
	public boolean isPre() {
		return (pn - 1 >= 1);
	}
	
	public int getNextPage() {
		if (isNext()) {
			return pn + 1;
		} else {
			return pn;
		}
	}
	public int getPrePage() {
		if (isPre()) {
			return pn - 1;
		} else {
			return pn;
		}
	}
}
