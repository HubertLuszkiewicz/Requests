package lab.requests.backing;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import lab.requests.data.RequestRepository;
import lab.requests.entities.Request;

import java.time.LocalDate;
import java.util.List;

@RequestScoped
@Named
public class RequestsList {
    @Inject
    private RequestRepository requestRepository;

    @Size(min = 3, max = 60, message = "{request.size}")
    private String newRequest;

    private jakarta.faces.component.html.HtmlDataTable requestDataTable;

    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Transactional
    public String addRequest() {
        Request request = new Request();
        LocalDate localDate = LocalDate.now();
        request.setRequestDate(localDate);
        request.setRequestText(newRequest);

        requestRepository.create(request);
        setNewRequest("");
        return "requestsList?faces-redirect=true";
    }

    @Transactional
    public String deleteRequest() {
        Request req = (Request) getRequestDataTable().getRowData();

        requestRepository.remove(req);
        return "requestsList?faces-redirect=true";
    }

    public String getNewRequest() {
        return newRequest;
    }

    public void setNewRequest(String newRequest) {
        this.newRequest = newRequest;
    }

    public jakarta.faces.component.html.HtmlDataTable getRequestDataTable() {
        return requestDataTable;
    }

    public void setRequestDataTable(jakarta.faces.component.html.HtmlDataTable requestDataTable) {
        this.requestDataTable = requestDataTable;
    }
}
