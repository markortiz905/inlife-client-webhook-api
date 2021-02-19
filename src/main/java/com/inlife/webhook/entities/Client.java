package com.inlife.webhook.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mark ortiz
 */
//@Entity
//@Table(name = "client")
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Client {
    @Id
    @EqualsAndHashCode.Include
    private Long id; //item_id

    private String name;

    private String addressContactInfoNotes;

    private String photo;

    private String navLinks;

    private String gender;
    private String dateOfBirth;
    private String clientPlanLink;
    private String aboutMe;
    private String safetyAlerts;
    private String communicatesBy;
    private String canDoIndependently;
    private String willNeedAssistanceWith;
    private String likes;
    private String dislikes;
    private String goalsAndPlansToAchieveThem;
    private String nominatedRepresenativeItemId;
    private String nominatedRepresenativeRelationshipWithClient;
    private String emergencyContactNextOfKinItemId;
    private String emergencyContactRelationshipWithClient;
    private String otherSupporterItemId;
    private String formalAdvocateitemId;
    private String supportCoordinatorItemId;
    private String doctorItemId;
    private String pharmacy;
    private String notesOtherKeyContacts;
    private String status;
    private String inlifeCoordinatorItemId;
    private String fileNotes;
    private String incidentReports;
    private String documentationRequiredFromAssistantOnDuty;
    private String emergencyManagementPlan;
    private String emergencyAlerts;
    private String sharedCalendarUse;
    private String linkToSharedCalendar;
    private String dateOfLastFormalClientMeeting;
    private String holdNextMeetingBy;
    private String generateClientPlanToShareWithClient;
    private String clientPlanPreparation;
    private String dateClientPlanWasLastSavedToShareFile;
    private String dateClientPlanWasLastApprovedByClient;
    private String dateClientPlanWasLastApprovedByDeputy;
    private String onboardingRequirementsCompleted;
    private String dateOnboardingRequirementsCompleted;
    private String notes;
    private String deputyCode;

}
