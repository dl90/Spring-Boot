package local.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import local.demo.entity.Department;
import local.demo.service.DepartmentService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DepartmentController.class)
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DepartmentService departmentService;

    private Department foo;
    private Department bar;

    @BeforeEach
    void setUp() {
        foo = new Department(1L, "Foo", "FO-1");
        bar = new Department(2L, "Bar", "BO-1");
    }

    @Test
    void saveDepartment() throws Exception {
        Department fooRequest = new Department();
        fooRequest.setDepartmentName(foo.getDepartmentName());
        fooRequest.setDepartmentCode(foo.getDepartmentCode());
        Mockito.when(departmentService.saveDepartment(fooRequest)).thenReturn(foo);

        ObjectMapper mapper = new ObjectMapper();
        String jsonPayload = mapper.writeValueAsString(fooRequest);
        MvcResult result = mockMvc.perform(post("/departments")
                .contentType(MediaType.APPLICATION_JSON).content(jsonPayload))
                .andExpect(status().isOk())
                .andReturn();

        // @TODO why is foo not returned when it should
        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    void getDepartmentById() throws Exception {
        Mockito.when(departmentService.findOne(bar.getDepartmentId())).thenReturn(bar);
        MvcResult result = mockMvc.perform(get("/departments/2")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.departmentCode").value(bar.getDepartmentCode()))
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        Department responseBar = mapper.readValue(result.getResponse().getContentAsString(), Department.class);
        Assertions.assertEquals(bar.getDepartmentId(), responseBar.getDepartmentId());
    }
}
