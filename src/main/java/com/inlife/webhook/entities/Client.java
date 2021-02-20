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
    private String needStaff;
    private String staffNeedsDetails;
    private String staffingPreference;
    private String generalRosteringNotes;
    private String assistanceNotSuitableItemId;
    private String whoPreparesRoster;
    private String forwardRosterRequired;
    private String timesheetApprovalMethod;
    private String clientOrNomineeApprover;
    private String addShiftDetailsToApprovalEmails;
    private String deputyLocationServesMultipleClients;
    private String doesThePersonIdentityAsIndigenous;
    private String indigenousCultureHeritageNotes;
    private String cultureAndOrIdentityIssuesToConsider;
    private String cultureAndIdentityNotes;
    private String livingArranagementAndDailyLife;
    private String familyInformalCareerInformation;
    private String pets;
    private String homeAssessments;
    private String wwcCheckRequired;
    private String otherClientSpecificIssuesToNote;
    private String primaryDisability;
    private String associatedDisabilityOrMedicalCondition;
    private String abilitiesAndLimitation;
    private String overallHealthVision;
    private String overallHealthHearing;
    private String overallHealthFalls;
    private String overallHealthPainManagement;
    private String overallHealthMemory;
    private String medicationSummary;
    private String inlifeRoleInMedicationAdministration;
    private String dateMedicationDetailsLastSavedToShareFile;
    private String currentHealthTherapistAlliedProfessionals;
    private String descriptionOfTherapySupportServices;
    private String mobilityAndTransfer;
    private String selfPersonalCareDressingEtc;
    private String continenceManagement;
    private String aidsAndEquipment;
    private String mealTimeAssistanceForPreparation;
    private String assistanceWithCommunication;
    private String householdTasks;
    private String primaryMethodsOfTransportWithInlife;
    private String additionalTranspoInfo;
    private String clientsVehicleInsurancePolicyOnShareFile;
    private String vehicleInsuranceRenewalDate;
    private String moneyHandling;
    private String supervisionAndSafety;
    private String behaviourSupportNeeds;
    private String dailySocialActivities;
    private String typicalRoutes;
    private String detailedRoutineOnShareFile;
    private String trainingRequirements;
    private String onCallNotes;
    private String primaryFundingSource;
    private String agencyId;
    private String otherFundingSources;
    private String billingMethods;
    private String planManager;//not tested;
    private String sendInvoiceTo;
    private String participantAddressesForServiceIfApplicable;
    private String fundingBudget;
    private String serviceAgreement;
    private String dateServiceAgreementWasSigned;
    private String dateOnboardingDocsAcknowledged;
    private String payGradeClassification;
    private String reasonsForSacsClassification;
    private String ndisPriceLevel;
    private String locationRegion;
    private String location;
    private String serviceDeliveryStartDate;
    private String serviceDeliveryEndDate;
    private String nextBirthdayDate;
}
