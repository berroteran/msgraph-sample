package ec.com.bancointernacional.msgraph.msgraphclient.serv.rest;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DeviceCodeCredential;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.microsoft.aad.msal4j.ClientCredentialFactory;
import com.microsoft.aad.msal4j.ClientCredentialParameters;
import com.microsoft.aad.msal4j.ConfidentialClientApplication;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.*;
import com.microsoft.graph.options.HeaderOption;
import com.microsoft.graph.options.Option;
import com.microsoft.graph.options.QueryOption;
import com.microsoft.graph.requests.EventCollectionPage;
import com.microsoft.graph.requests.GraphServiceClient;
import ec.com.bancointernacional.msgraph.msgraphclient.model.*;
import ec.com.bancointernacional.msgraph.msgraphclient.utils.Fechas;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/agent")
@Produces(MediaType.APPLICATION_JSON)
public class AgendaController {

    private static String TENANTID = "";
    private static String authority;
    private static String clientId;
    private static String secret;
    private static String scope;
    private static ConfidentialClientApplication app;
    private static DeviceCodeCredential _deviceCodeCredential;
    private static String USER_AGENDA_ID;
    private static String TOKEN_CONNECTION = "";
    private static ClientSecretCredential clientSecretCredential;
    private static GraphServiceClient graphClient;
    private static TokenCredentialAuthProvider tokenCredentialAuthProvider;




    @GetMapping(path = "/getDisponibility",
            consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Response signin(@RequestBody @Valid AgentRequest arequest) {
        Response r = new Response();
        try {
            mtdValidaToken();
            r = getUserCalendar(TOKEN_CONNECTION);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Oops! We have an exception of type - " + ex.getClass());
            System.out.println("Exception message - " + ex.getMessage());
        }

        return r;
    }


    @PostMapping(path = "/setSchedule",
            consumes = org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
            produces = org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
    public Response setSchedule(AgentScheduleRequest request) {

        Event event = new Event();
        event.subject = "sesion de prueba event";
        DateTimeTimeZone start = new DateTimeTimeZone();
        start.dateTime = "2022-09-16T09:02:00";
        start.timeZone = "America/Guayaquil";
        event.start = start;
        DateTimeTimeZone end = new DateTimeTimeZone();
        end.dateTime = "2022-09-23T09:32:00";
        end.timeZone = "America/Guayaquil";
        event.end = end;


        ItemBody body = new ItemBody();
        body.contentType = BodyType.HTML;
        body.content = "Does noon work for you?";
        event.body = body;
        Location location = new Location();
        location.displayName = "Harry's Bar";
        event.location = location;
        LinkedList<Attendee> attendeesList = new LinkedList<Attendee>();
        Attendee attendees = new Attendee();
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.address = "jcuascota@bancointernacional.ec";
        emailAddress.name = "Jose Cuascota";
        attendees.emailAddress = emailAddress;
        attendees.type = AttendeeType.REQUIRED;
        attendeesList.add(attendees);

        event.attendees = attendeesList;
        event.allowNewTimeProposals = true;

        try {
            mtdValidaToken();


            graphClient.users(USER_AGENDA_ID).events()
                    .buildRequest()
                    .post(event);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return new Response();
    }


    private void createClients() {
        tokenCredentialAuthProvider = new TokenCredentialAuthProvider(Collections.singletonList("https://graph.microsoft.com/.default"), clientSecretCredential);
        graphClient =
                GraphServiceClient
                        .builder()
                        .authenticationProvider(tokenCredentialAuthProvider)
                        .buildClient();

    }

    public String getIDfromEmail(String email) {
        User user = graphClient.users("jcuascota@bancointernacional.ec")
                .buildRequest()
                .get();

        return user.id;
    }


    private static AgentScheduleResponse getUserCalendar(String accessToken) {
        LinkedList<Option> requestOptions = new LinkedList<Option>();

        requestOptions.add(new HeaderOption("Prefer", "outlook.timezone=\"America/Bogota\""));
        requestOptions.add(new QueryOption("startdatetime", Fechas.getDateToday()));
        requestOptions.add(new QueryOption("enddatetime", Fechas.addDaysTodaySkippingWeekends(5)));
        requestOptions.add(new QueryOption("orderby", "lastModifiedDateTime"));


        EventCollectionPage calendarView = graphClient.users(USER_AGENDA_ID).calendarView()
                .buildRequest(requestOptions)
                .get();

        AgentScheduleDay agd = new AgentScheduleDay();

        System.out.println("================================ ");
        for (Event s : calendarView.getCurrentPage()) {
            agd.addHora(new AgentScheduleHora(s.start.dateTime, s.end.dateTime, false));
            System.out.println("Start : " + s.start.dateTime);
            System.out.println("End   : " + s.end.dateTime);
            System.out.println("Asunto: " + s.subject);
            System.out.println(s.bodyPreview);
            System.out.println(" ----------------------------------------  ");
        }
        System.out.println("================================ ");
        List<AgentScheduleDay> listDay = new ArrayList<>();
        listDay.add(agd);
        AgentScheduleResponse responseAgent = new AgentScheduleResponse();
        responseAgent.setAgenteID(777);
        responseAgent.setDays(listDay);

        return responseAgent;
    }


    private static IAuthenticationResult getAccessTokenByClientCredentialGrant() throws Exception {

        // With client credentials flows the scope is ALWAYS of the shape "resource/.default", as the
        // application permissions need to be set statically (in the portal), and then granted by a tenant administrator
        ClientCredentialParameters clientCredentialParam = ClientCredentialParameters.builder(
                        Collections.singleton(scope))
                .build();

        CompletableFuture<IAuthenticationResult> future = app.acquireToken(clientCredentialParam);
        return future.get();
    }

    private static void BuildConfidentialClientObject() throws Exception {
        // Load properties file and set properties used throughout the sample
        app = ConfidentialClientApplication.builder(
                        clientId,
                        ClientCredentialFactory.createFromSecret(secret))
                .authority(authority)
                .build();

        clientSecretCredential = new ClientSecretCredentialBuilder()
                .clientId(clientId)
                .clientSecret(secret)
                .tenantId(TENANTID)
                .build();

    }

    private static void setUpSampleData() throws IOException {
        // Load properties file and set properties used throughout the sample
        Properties properties = new Properties();
        properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
        authority = properties.getProperty("AUTHORITY");
        clientId = properties.getProperty("CLIENT_ID");
        secret = properties.getProperty("SECRET");
        scope = properties.getProperty("SCOPE");
        TENANTID = properties.getProperty("TENANTID");
        USER_AGENDA_ID = properties.getProperty("USER_AGENDA_ID");
    }

    private String mtdJSON2Strign(Response r) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(r);
    }

    private void mtdValidaToken() throws Exception {
        if (TOKEN_CONNECTION.isEmpty()) {
            setUpSampleData();
            BuildConfidentialClientObject();
            //token
            IAuthenticationResult getToken = getAccessTokenByClientCredentialGrant();
            createClients();
            TOKEN_CONNECTION = getToken.accessToken();
        }
    }
}
