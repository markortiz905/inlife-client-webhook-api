package com.inlife.webhook.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author mark ortiz
 */
@Entity
@Table(name = "client")
@Builder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(exclude = {"jsonSource"})
public class Client {
    @Id
    @EqualsAndHashCode.Include
    private Long id; //item_id

    private String name;

    @Column(columnDefinition = "text")
    private String addressContactInfoNotes;
    @Column(columnDefinition = "text")
    private String photo;
    @Column(columnDefinition = "text")
    private String navLinks;
    private String gender;
    private String dateOfBirth;
    @Column(columnDefinition = "text")
    private String clientPlanLink;
    @Column(columnDefinition = "text")
    private String aboutMe;
    @Column(columnDefinition = "text")
    private String safetyAlerts;
    @Column(columnDefinition = "text")
    private String communicatesBy;
    @Column(columnDefinition = "text")
    private String canDoIndependently;
    @Column(columnDefinition = "text")
    private String willNeedAssistanceWith;
    @Column(columnDefinition = "text")
    private String likes;
    @Column(columnDefinition = "text")
    private String dislikes;
    @Column(columnDefinition = "text")
    private String goalsAndPlansToAchieveThem;
    @Column(columnDefinition = "text")
    private String nominatedRepresenativeItemId;
    @Column(columnDefinition = "text")
    private String nominatedRepresenativeRelationshipWithClient;
    @Column(columnDefinition = "text")
    private String emergencyContactNextOfKinItemId;
    @Column(columnDefinition = "text")
    private String emergencyContactRelationshipWithClient;
    @Column(columnDefinition = "text")
    private String otherSupporterItemId;
    @Column(columnDefinition = "text")
    private String formalAdvocateitemId;
    @Column(columnDefinition = "text")
    private String supportCoordinatorItemId;
    @Column(columnDefinition = "text")
    private String doctorItemId;
    @Column(columnDefinition = "text")
    private String pharmacy;
    @Column(columnDefinition = "text")
    private String notesOtherKeyContacts;
    @Column(columnDefinition = "text")
    private String status;
    private String inlifeCoordinatorItemId;
    @Column(columnDefinition = "text")
    private String fileNotes;
    @Column(columnDefinition = "text")
    private String incidentReports;
    @Column(columnDefinition = "text")
    private String documentationRequiredFromAssistantOnDuty;
    @Column(columnDefinition = "text")
    private String emergencyManagementPlan;
    @Column(columnDefinition = "text")
    private String emergencyAlerts;
    @Column(columnDefinition = "text")
    private String sharedCalendarUse;
    @Column(columnDefinition = "text")
    private String linkToSharedCalendar;
    @Column(columnDefinition = "text")
    private String dateOfLastFormalClientMeeting;
    @Column(columnDefinition = "text")
    private String holdNextMeetingBy;
    @Column(columnDefinition = "text")
    private String generateClientPlanToShareWithClient;
    @Column(columnDefinition = "text")
    private String clientPlanPreparation;
    @Column(columnDefinition = "text")
    private String dateClientPlanWasLastSavedToShareFile;
    @Column(columnDefinition = "text")
    private String dateClientPlanWasLastApprovedByClient;
    @Column(columnDefinition = "text")
    private String dateClientPlanWasLastApprovedByDeputy;
    @Column(columnDefinition = "text")
    private String onboardingRequirementsCompleted;
    private String dateOnboardingRequirementsCompleted;
    @Column(columnDefinition = "text")
    private String notes;
    @Column(columnDefinition = "text")
    private String deputyCode;
    @Column(columnDefinition = "text")
    private String needStaff;
    @Column(columnDefinition = "text")
    private String staffNeedsDetails;
    @Column(columnDefinition = "text")
    private String staffingPreference;
    @Column(columnDefinition = "text")
    private String generalRosteringNotes;
    @Column(columnDefinition = "text")
    private String assistanceNotSuitableItemId;
    @Column(columnDefinition = "text")
    private String whoPreparesRoster;
    @Column(columnDefinition = "text")
    private String forwardRosterRequired;
    @Column(columnDefinition = "text")
    private String timesheetApprovalMethod;
    @Column(columnDefinition = "text")
    private String clientOrNomineeApprover;
    @Column(columnDefinition = "text")
    private String addShiftDetailsToApprovalEmails;
    @Column(columnDefinition = "text")
    private String deputyLocationServesMultipleClients;
    @Column(columnDefinition = "text")
    private String doesThePersonIdentityAsIndigenous;
    @Column(columnDefinition = "text")
    private String indigenousCultureHeritageNotes;
    @Column(columnDefinition = "text")
    private String cultureAndOrIdentityIssuesToConsider;
    @Column(columnDefinition = "text")
    private String cultureAndIdentityNotes;
    @Column(columnDefinition = "text")
    private String livingArranagementAndDailyLife;
    @Column(columnDefinition = "text")
    private String familyInformalCareerInformation;
    @Column(columnDefinition = "text")
    private String pets;
    @Column(columnDefinition = "text")
    private String homeAssessments;
    @Column(columnDefinition = "text")
    private String wwcCheckRequired;
    @Column(columnDefinition = "text")
    private String otherClientSpecificIssuesToNote;
    @Column(columnDefinition = "text")
    private String primaryDisability;
    @Column(columnDefinition = "text")
    private String associatedDisabilityOrMedicalCondition;
    @Column(columnDefinition = "text")
    private String abilitiesAndLimitation;
    @Column(columnDefinition = "text")
    private String overallHealthVision;
    @Column(columnDefinition = "text")
    private String overallHealthHearing;
    @Column(columnDefinition = "text")
    private String overallHealthFalls;
    @Column(columnDefinition = "text")
    private String overallHealthPainManagement;
    @Column(columnDefinition = "text")
    private String overallHealthMemory;
    @Column(columnDefinition = "text")
    private String medicationSummary;
    @Column(columnDefinition = "text")
    private String inlifeRoleInMedicationAdministration;
    @Column(columnDefinition = "text")
    private String dateMedicationDetailsLastSavedToShareFile;
    @Column(columnDefinition = "text")
    private String currentHealthTherapistAlliedProfessionals;
    @Column(columnDefinition = "text")
    private String descriptionOfTherapySupportServices;
    @Column(columnDefinition = "text")
    private String mobilityAndTransfer;
    @Column(columnDefinition = "text")
    private String selfPersonalCareDressingEtc;
    @Column(columnDefinition = "text")
    private String continenceManagement;
    @Column(columnDefinition = "text")
    private String aidsAndEquipment;
    @Column(columnDefinition = "text")
    private String mealTimeAssistanceForPreparation;
    @Column(columnDefinition = "text")
    private String assistanceWithCommunication;
    @Column(columnDefinition = "text")
    private String householdTasks;
    @Column(columnDefinition = "text")
    private String primaryMethodsOfTransportWithInlife;
    @Column(columnDefinition = "text")
    private String additionalTranspoInfo;
    @Column(columnDefinition = "text")
    private String clientsVehicleInsurancePolicyOnShareFile;
    @Column(columnDefinition = "text")
    private String vehicleInsuranceRenewalDate;
    @Column(columnDefinition = "text")
    private String moneyHandling;
    @Column(columnDefinition = "text")
    private String supervisionAndSafety;
    @Column(columnDefinition = "text")
    private String behaviourSupportNeeds;
    @Column(columnDefinition = "text")
    private String dailySocialActivities;
    @Column(columnDefinition = "text")
    private String typicalRoutes;
    @Column(columnDefinition = "text")
    private String detailedRoutineOnShareFile;
    @Column(columnDefinition = "text")
    private String trainingRequirements;
    @Column(columnDefinition = "text")
    private String onCallNotes;
    @Column(columnDefinition = "text")
    private String primaryFundingSource;
    @Column(columnDefinition = "text")
    private String agencyId;
    @Column(columnDefinition = "text")
    private String otherFundingSources;
    @Column(columnDefinition = "text")
    private String billingMethods;
    @Column(columnDefinition = "text")
    private String planManager;//not tested;
    @Column(columnDefinition = "text")
    private String sendInvoiceTo;
    @Column(columnDefinition = "text")
    private String participantAddressesForServiceIfApplicable;
    @Column(columnDefinition = "text")
    private String fundingBudget;
    @Column(columnDefinition = "text")
    private String serviceAgreement;
    @Column(columnDefinition = "text")
    private String dateServiceAgreementWasSigned;
    @Column(columnDefinition = "text")
    private String dateOnboardingDocsAcknowledged;
    @Column(columnDefinition = "text")
    private String payGradeClassification;
    @Column(columnDefinition = "text")
    private String reasonsForSacsClassification;
    @Column(columnDefinition = "text")
    private String ndisPriceLevel;
    @Column(columnDefinition = "text")
    private String locationRegion;
    @Column(columnDefinition = "text")
    private String location;
    @Column(columnDefinition = "text")
    private String serviceDeliveryStartDate;
    @Column(columnDefinition = "text")
    private String serviceDeliveryEndDate;
    @Column(columnDefinition = "text")
    private String nextBirthdayDate;
    @Column(name = "jsonSource", columnDefinition = "json")
    @JsonRawValue
    @JsonIgnore
    private String jsonSource;

}
