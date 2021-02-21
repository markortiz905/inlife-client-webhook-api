package com.inlife.webhook.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inlife.webhook.dto.ClientRequestDto;
import com.inlife.webhook.entities.Client;
import com.inlife.webhook.exception.BadRequestServiceException;
import com.inlife.webhook.exception.ServiceException;
import com.inlife.webhook.repositories.jpa.JsonWebhookRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@Primary
@Service
public class JsonWebhookServiceImpl implements JsonWebhookService {

    @Autowired
    private JsonWebhookRepository jsonWebhookRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    @Async
    public CompletableFuture<Void> saveClientAsync(ClientRequestDto request, @NonNull String rawJson) throws ServiceException, BadRequestServiceException {
        return CompletableFuture.supplyAsync(() -> {
            Long id = request.getItem_id();
            Client client = new Client();
            client.setId(id);
            request.getFields().parallelStream().forEach( field -> {
                try {
                    //log.info("label: " + field.getLabel());
                    if (field.getLabel().equalsIgnoreCase("name")) {
                        String name = (String)((Map<String, Object>)field.getValues().get(0).getValue()).get("title");
                        client.setName(name);
                    } else if (field.getLabel().equalsIgnoreCase("Address & contact info notes")) {
                        String info = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setAddressContactInfoNotes(info);
                    } else if (field.getLabel().equalsIgnoreCase("photo")) {
                        String photo = mapper.writeValueAsString(field.getValues().get(0).getValue());
                        client.setPhoto(photo);
                    } else if (field.getLabel().equalsIgnoreCase("nav links")) {
                        String navLinks = (String)field.getValues().get(0).getValue();
                        client.setNavLinks(navLinks);
                    } else if (field.getLabel().equalsIgnoreCase("gender")) {
                        String gender = (String)((Map<String, Object>)field.getValues().get(0).getValue()).get("text");
                        client.setGender(gender);
                    } else if (field.getLabel().equalsIgnoreCase("Date of birth")) {
                        String dob = field.getValues().get(0).getStart_date();
                        client.setDateOfBirth(dob);
                    } else if (field.getLabel().equalsIgnoreCase("Client Plan link")) { //might be multiple based on the picture from G
                        String embed = mapper.writeValueAsString(field.getValues().get(0).getEmbed());
                        client.setClientPlanLink(embed);
                    } else if (field.getLabel().equalsIgnoreCase("About me")) {
                        String aboutMe = (String)field.getValues().get(0).getValue();
                        client.setAboutMe(aboutMe);
                    } else if (field.getLabel().equalsIgnoreCase("Health and safety alerts")) {
                        String safetyAlerts = (String)field.getValues().get(0).getValue();
                        client.setSafetyAlerts(safetyAlerts);
                    } else if (field.getLabel().equalsIgnoreCase("Communicates by")) {
                        String communicatesBy = (String)field.getValues().get(0).getValue();
                        client.setCommunicatesBy(communicatesBy);
                    } else if (field.getLabel().equalsIgnoreCase("Can do the following independently")) {
                        String cando = (String)field.getValues().get(0).getValue();
                        client.setCanDoIndependently(cando);
                    } else if (field.getLabel().equalsIgnoreCase("Will need assistance with")) {
                        String assistance = (String)field.getValues().get(0).getValue();
                        client.setWillNeedAssistanceWith(assistance);
                    } else if (field.getLabel().equalsIgnoreCase("Likes")) {
                        String likes = (String)field.getValues().get(0).getValue();
                        client.setLikes(likes);
                    } else if (field.getLabel().equalsIgnoreCase("Dislikes")) {
                        String dlikes = (String)field.getValues().get(0).getValue();
                        client.setDislikes(dlikes);
                    } else if (field.getLabel().equalsIgnoreCase("Goals and plans to achieve them")) {
                        String plans = (String) field.getValues().get(0).getValue();
                        client.setGoalsAndPlansToAchieveThem(plans);
                    } else if (field.getLabel().equalsIgnoreCase("Nominated representative")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setNominatedRepresenativeItemId(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Nom. rep. role and relationship with client")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        client.setNominatedRepresenativeRelationshipWithClient(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Emergency contact / next of kin")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setEmergencyContactNextOfKinItemId(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Emerg. contact relationship with client")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        client.setEmergencyContactRelationshipWithClient(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Other supporters")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setOtherSupporterItemId(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Formal advocate")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setFormalAdvocateitemId(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Support coordinator")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setSupportCoordinatorItemId(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Doctor")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setDoctorItemId(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Pharmacy")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        client.setPharmacy(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Notes/Other key contacts")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        client.setNotesOtherKeyContacts(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Status")) {
                        String rel = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        client.setStatus(rel);
                    } else if (field.getLabel().equalsIgnoreCase("InLife Coordinator")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setInlifeCoordinatorItemId(rel);
                    } else if (field.getLabel().equalsIgnoreCase("File Notes")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        client.setFileNotes(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Incident Reports")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        client.setIncidentReports(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Documentation required from Assistants on shift")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        client.setDocumentationRequiredFromAssistantOnDuty(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Emergency management plan")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        client.setEmergencyManagementPlan(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Emergency alerts")) {
                        String rel = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        client.setEmergencyAlerts(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Shared calendar use")) {
                        String rel = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        client.setSharedCalendarUse(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Link to shared calendar")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getValue().toString()).collect(Collectors.joining(","));
                        client.setLinkToSharedCalendar(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Date of last formal client meeting")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getStart_date()).collect(Collectors.joining(","));
                        client.setDateOfLastFormalClientMeeting(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Hold next meeting by")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getStart_date()).collect(Collectors.joining(","));
                        client.setHoldNextMeetingBy(rel);
                    } else if (field.getLabel().equalsIgnoreCase("[Old] Generate client plan to share with client")) {
                        String rel = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        client.setGenerateClientPlanToShareWithClient(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Client plan preparation")) {
                        String rel = field.getValues().parallelStream().map( val -> (String)((Map<String, Object>)val.getValue()).get("text")).collect(Collectors.joining(","));
                        client.setClientPlanPreparation(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Date client plan was last saved to ShareFile")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getStart_date()).collect(Collectors.joining(","));
                        client.setDateClientPlanWasLastSavedToShareFile(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Date client plan was last approved by client")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getStart_date()).collect(Collectors.joining(","));
                        client.setDateClientPlanWasLastApprovedByClient(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Date client plan was last posted on Deputy")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getStart_date()).collect(Collectors.joining(","));
                        client.setDateClientPlanWasLastApprovedByDeputy(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Onboarding requirements completed")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setOnboardingRequirementsCompleted(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Date onboarding requirements completed")) {
                        String rel = field.getValues().parallelStream().map( val -> val.getStart_date()).collect(Collectors.joining(","));
                        client.setDateOnboardingRequirementsCompleted(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Notes")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setNotes(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Deputy code")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setDeputyCode(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Needs staff")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setNeedStaff(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Staffing needs - details")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setStaffNeedsDetails(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Staffing preferences")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setStaffingPreference(rel);
                    } else if (field.getLabel().equalsIgnoreCase("General rostering notes")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setGeneralRosteringNotes(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Assistants not suitable")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setAssistanceNotSuitableItemId(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Who prepares the roster")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setWhoPreparesRoster(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Forward roster required")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setForwardRosterRequired(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Timesheet approval method")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setTimesheetApprovalMethod(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Client or nominee approver")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setClientOrNomineeApprover(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Add shift details to the approval emails?")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setAddShiftDetailsToApprovalEmails(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Deputy location serves multiple clients")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setDeputyLocationServesMultipleClients(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Does the person identify as indigenous")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setDoesThePersonIdentityAsIndigenous(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Indigenous culture/heritage notes")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setIndigenousCultureHeritageNotes(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Culture and/or identity issues to consider")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setCultureAndOrIdentityIssuesToConsider(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Culture and/or identity notes")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setCultureAndIdentityNotes(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Living arrangements and daily life")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setLivingArranagementAndDailyLife(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Family/informal carer information")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setFamilyInformalCareerInformation(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Pets")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setPets(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Home assessment")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setHomeAssessments(rel);
                    } else if (field.getLabel().equalsIgnoreCase("WWC check required")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setWwcCheckRequired(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Other client specific issues to note")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setOtherClientSpecificIssuesToNote(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Primary disability")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setPrimaryDisability(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Associated disability or medical condition")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setAssociatedDisabilityOrMedicalCondition(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Abilities/limitations")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setAbilitiesAndLimitation(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Overall health: Vision")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setOverallHealthVision(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Overall health: Hearing")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setOverallHealthHearing(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Overall health: Falls")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setOverallHealthFalls(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Overall health: Pain management")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setOverallHealthPainManagement(rel);
                    }  else if (field.getLabel().equalsIgnoreCase("Overall health: memory")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setOverallHealthMemory(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Medication summary")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setMedicationSummary(rel);
                    } else if (field.getLabel().equalsIgnoreCase("InLife role in medication administration?")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setInlifeRoleInMedicationAdministration(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Date medication details last saved to ShareFile")) {
                        String rel = field.getValues().get(0).getStart_date();
                        client.setDateMedicationDetailsLastSavedToShareFile(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Current therapists/allied health professionals")) {
                        String rel = field.getValues().parallelStream().map( val -> "" + ((Map<String, Object>)val.getValue()).get("item_id")).collect(Collectors.joining(","));
                        client.setCurrentHealthTherapistAlliedProfessionals(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Description of therapy support services")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setDescriptionOfTherapySupportServices(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Mobility and transfers")) {
                        String rel = (String)field.getValues().get(0).getValue();
                        client.setMobilityAndTransfer(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Self /personal care/dressing etc")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setSelfPersonalCareDressingEtc(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Continence management")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setContinenceManagement(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Aids and equipment")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setAidsAndEquipment(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Mealtime assistance / food preparation")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setMealTimeAssistanceForPreparation(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Assistance with communication")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setAssistanceWithCommunication(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Household tasks")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setHouseholdTasks(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Primary method(s) of transport with InLife")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setPrimaryMethodsOfTransportWithInlife(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Additional transport info")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setAdditionalTranspoInfo(rel);
                    } else if (field.getLabel().equalsIgnoreCase("Client's vehicle insurance policy on ShareFile")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setClientsVehicleInsurancePolicyOnShareFile(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Vehicle insurance renewal date")) {
                        String rel = field.getValues().get(0).getStart_date();
                        client.setVehicleInsuranceRenewalDate(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Money handling")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setMoneyHandling(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Supervision and safety")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setSupervisionAndSafety(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Behaviour support needs")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setBehaviourSupportNeeds(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Daily/social activities")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setDailySocialActivities(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Typical routine")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setTypicalRoutes(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Detailed routine on ShareFile")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setDetailedRoutineOnShareFile(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Training requirements")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setTrainingRequirements(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("On-call notes")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setOnCallNotes(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Primary funding source")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setPrimaryFundingSource(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Agency ID")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setAgencyId(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Other funding source(s)")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setOtherFundingSources(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Billing method")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setBillingMethods(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Plan manager (if applicable)")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setPlanManager(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Send invoices to")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setSendInvoiceTo(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Participant address(es) for service (if applicable)")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setParticipantAddressesForServiceIfApplicable(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Funding budgets")) {
                        String rel = field.getValues().stream().map(val-> (String)val.getValue()).collect(Collectors.joining(","));
                        client.setFundingBudget(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Service agreement")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setServiceAgreement(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Date Service agreement was signed")) {
                        String rel = field.getValues().get(0).getStart_date();
                        client.setDateServiceAgreementWasSigned(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Date onboarding docs acknowledged")) {
                        String rel = field.getValues().get(0).getStart_date();
                        client.setDateOnboardingDocsAcknowledged(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Pay grade classification")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setPayGradeClassification(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Reason for SACS classification")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setReasonsForSacsClassification(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("NDIS price level")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setNdisPriceLevel(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Location (region)")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setLocationRegion(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Location")) {
                        String rel = field.getValues().stream().map(val-> (Map<String, Object>)val.getValue()).map(map -> (String)map.get("text")).collect(Collectors.joining(","));
                        client.setLocation(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Service delivery start date")) {
                        String rel = field.getValues().get(0).getStart_date();
                        client.setServiceDeliveryStartDate(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Service delivery end date")) {
                        String rel = field.getValues().get(0).getStart_date();
                        client.setServiceDeliveryEndDate(rel);
                    } else if (field.getLabel().trim().equalsIgnoreCase("Next Birthday Date")) {
                        String rel = field.getValues().get(0).getStart_date();
                        client.setNextBirthdayDate(rel);
                    }
                } catch (Exception e) {log.error(e.getMessage());}
            });
            return client;
        }).thenAccept(result -> {
            log.info("saving client -> " + result.getId() + " ");
            result.setJsonSource(rawJson);
            /*try {
                log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }*/
            Client saved = jsonWebhookRepository.save(result);
            log.info("client entry saved with id : " + saved.getId());
        }).exceptionally( e -> { e.printStackTrace(); return null;}).thenRun(() -> log.info("finished saving client entry"));
    }

}
