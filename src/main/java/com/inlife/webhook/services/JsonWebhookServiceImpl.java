package com.inlife.webhook.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inlife.webhook.dto.ClientRequestDto;
import com.inlife.webhook.entities.Client;
import com.inlife.webhook.entities.JsonWebhook;
import com.inlife.webhook.exception.BadRequestServiceException;
import com.inlife.webhook.exception.ServiceException;
import com.inlife.webhook.repositories.jpa.JsonWebhookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Primary
@Service
public class JsonWebhookServiceImpl implements JsonWebhookService {

    @Autowired
    private JsonWebhookRepository jsonWebhookRepository;

    @Autowired
    private ObjectMapper mapper;

    @Override
    public List<JsonWebhook> save(String payload, Function<String, List<JsonWebhook>> toObject) throws ServiceException, BadRequestServiceException {
        return StreamSupport.stream(jsonWebhookRepository.saveAll(toObject.apply(payload)).spliterator(), false)
                        .collect(Collectors.toList());
    }

    @Override
    @Async
    public CompletableFuture<Void> saveClientAsync(ClientRequestDto request) throws ServiceException, BadRequestServiceException {
        return CompletableFuture.supplyAsync(() -> {
            Long id = request.getItem_id();
            boolean exist = jsonWebhookRepository.existsById(id);

            Client client = new Client();
            client.setId(id);

            request.getFields().parallelStream().forEach( field -> {
                try {
                    log.info("label: " + field.getLabel());
                    if (field.getLabel().equalsIgnoreCase("name")) {
                        String name = (String)((Map<String, Object>)field.getValues().get(0).getValue()).get("title");
                        client.setName(name);
                    } else if (field.getLabel().equalsIgnoreCase("Address & contact info notes")) {
                        String info = (String)field.getValues().get(0).getValue();
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
                        String rel = (String)((Map<String, Object>)field.getValues().get(0).getValue()).get("text");
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
                    }
                } catch (Exception e) {log.error(e.getMessage());}
            });

            return client;
        }).thenAccept(result -> {
            log.info("saved client -> " + result.getId() + " ");
            try {
                log.info(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            //jsonWebhookRepository.save(result);
        }).exceptionally( e -> { e.printStackTrace(); return null;}).thenRun(() -> log.info("finished saving entry"));
    }

    @Override
    @Async
    public CompletableFuture<Void> saveEntry(Map<String, Object> object) throws ServiceException, BadRequestServiceException {
        return CompletableFuture.supplyAsync(() -> {
            JsonWebhook hooks = new JsonWebhook();
            Long id = Long.valueOf(((Integer)((Map<String, Object>)object).get("item_id")).toString());
            boolean exist = jsonWebhookRepository.existsById(id);
            log.info("item with ID " + id + " does not exist, creating entry...");
            if (!exist) {
                hooks.setDateCreated(LocalDate.now());
            }
            hooks.setDateUpdated(LocalDate.now());
            hooks.setId(id);
            List<Object> fields = (List<Object>)((Map<String, Object>) object).get("fields");
            fields.stream().forEach( f -> {
                try {
                    Map<String, Object> field = (Map<String, Object>) f;
                    if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("your name")) {
                        hooks.setFullName((String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("primary inlife contact")) {
                        hooks.setPrimaryInlifeContact((String) ((Map<String, Object>) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value")).get("title"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("date opened")) {
                        hooks.setDateOpened(LocalDate.parse(
                                (String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("start_date"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("nature of disability")) {
                        hooks.setNatureOfDisability((String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("please describe the supports required ")) {
                        hooks.setSupportsRequiredDescription((String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("suburb")) {
                        hooks.setSuburb((String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).equalsIgnoreCase("expected time to onboard")) {
                        hooks.setExpectedTimeToOnboard((String) ((Map<String, Object>) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value")).get("text"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).equalsIgnoreCase("status")) {
                        hooks.setStatus((String) ((Map<String, Object>) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value")).get("text"));
                    }
                } catch (Exception e) {}
            });
            try {
                hooks.setJsonString(mapper.writeValueAsString(object));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return hooks;
        })
                .thenAccept(result -> {
                    log.info("saved entry -> " + result.getId() + " ");
                    jsonWebhookRepository.save(result);
                })
                .exceptionally( e -> { e.printStackTrace(); return null;})
                .thenRun(() -> log.info("finished saving entry"));
    }

    @Override
    @Async
    public CompletableFuture<Void> saveAsync(Map<String, Object> object) throws ServiceException, BadRequestServiceException {
        List<Object> objects = (List<Object>)object.get("items");
        return CompletableFuture.supplyAsync(() -> objects.parallelStream().map(obj -> {
            JsonWebhook hooks = new JsonWebhook();
            Long id = Long.valueOf(((Integer)((Map<String, Object>)obj).get("item_id")).toString());
            boolean exist = jsonWebhookRepository.existsById(id);
            log.info("item with ID " + id + " does not exist, creating entry...");
            if (!exist) {
                hooks.setDateCreated(LocalDate.now());
            }
            hooks.setDateUpdated(LocalDate.now());
            hooks.setId(id);
            List<Object> fields = (List<Object>)((Map<String, Object>) obj).get("fields");
            fields.stream().forEach( f -> {
                try {
                    Map<String, Object> field = (Map<String, Object>) f;
                    if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("your name")) {
                        hooks.setFullName((String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("primary inlife contact")) {
                        hooks.setPrimaryInlifeContact((String) ((Map<String, Object>) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value")).get("title"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("date opened")) {
                        hooks.setDateOpened(LocalDate.parse(
                                (String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("start_date"),
                                DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("nature of disability")) {
                        hooks.setNatureOfDisability((String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("please describe the supports required ")) {
                        hooks.setSupportsRequiredDescription((String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).contains("suburb")) {
                        hooks.setSuburb((String) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).equalsIgnoreCase("expected time to onboard")) {
                        hooks.setExpectedTimeToOnboard((String) ((Map<String, Object>) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value")).get("text"));
                    } else if (((String) field.get("label")).toLowerCase(Locale.ROOT).equalsIgnoreCase("status")) {
                        hooks.setStatus((String) ((Map<String, Object>) ((Map<String, Object>) ((List<Object>) field.get("values")).get(0)).get("value")).get("text"));
                    }
                } catch (Exception e) {}
            });
            try {
                hooks.setJsonString(mapper.writeValueAsString(obj));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return hooks;
        }).collect(Collectors.toList()))
                .thenAccept(result -> {
                    log.info("saving all " + result.size() + " entries to db..");
                    jsonWebhookRepository.saveAll(result);
                })
                .exceptionally( e -> { e.printStackTrace(); return null;})
                .thenRun(() -> log.info("finished saving all jsonwebhooks"));
    }
}
