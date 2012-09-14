package com.jstudio.jrestoa.domain;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.jstudio.jrestoa.domain.exception.BusinessException;
import com.jstudio.jrestoa.hbm.HbmManager;

@Entity
@DiscriminatorValue("1")
public class Supervisor extends Employee {
	public void permitAskOff(AskOffRecord record) throws BusinessException {
		if (record != null && !record.getVerified()) {
			Department dep = record.getEmployee().getDepartment();
			if (dep == null || !dep.equals(this.getDepartment())) {
				return;
			}
			record.setVerified(true);
			record.setSigner(this);
			record.setSignTime(new Timestamp(System.currentTimeMillis()));
			record.update();
			Mail mail = new Mail();
			mail.setTitle("请假已经获得批准");
			StringBuffer buffer = new StringBuffer();
			buffer.append("<p>您在");
			buffer.append(record.getApplyTime());
			buffer.append("申请的请假（理由：");
			buffer.append(record.getReason());
			buffer.append("）已经获得批准。</p>");
			buffer.append("<p>签字主管：");
			buffer.append(record.getSigner().getName());
			buffer.append("</p>");
			buffer.append("<p>签字时间：");
			buffer.append(record.getSignTime());
			buffer.append("</p>");
			mail.setBody(buffer.toString());
			this.sendMail(record.getEmployee().getLoginName(), mail);
		}
	}

	@SuppressWarnings("unchecked")
	public List<AskOffRecord> listUnsignedAskOff() {
		return HbmManager.query(AskOffRecord.class,
				"employee.department=? and permitted=false",
				new Object[] { this.getDepartment() });
	}
}
