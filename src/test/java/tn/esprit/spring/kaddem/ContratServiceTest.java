package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(ContratControllerTest.class)
 class ContratControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContratServiceImpl contratService;

    @Test
    public void testCalculChiffreAffaireEntreDeuxDates() throws Exception {
        // Arrange
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = sdf.parse("2024-01-01");
        Date endDate = sdf.parse("2024-03-01");
        float expectedChiffreAffaire = 3000.0f; // Example expected result

        // Mock the service call
        Mockito.when(contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate)).thenReturn(expectedChiffreAffaire);

        // Act and Assert
        mockMvc.perform(get("/calculChiffreAffaireEntreDeuxDate/2024-01-01/2024-03-01"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedChiffreAffaire)));
    }
}
