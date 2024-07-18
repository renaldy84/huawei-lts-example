package id.sonar.experiment.lts_example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.commons.lang3.StringUtils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import id.sonar.experiment.lts_example.util.RestTemplateHttpUtil;
import lombok.Setter;



@ExtendWith(MockitoExtension.class)
public class AuthTokenApiServiceTest {
   
    private String authEndpointString;
    private String domain;
    private String username;
    private String password;
    @Mock
    private RestTemplateHttpUtil restTemplateHttpUtil;
    

    @Test
    public void canGetToken(){
        domain = "domain.com";
        username = "foo";
        password = "bar";
        authEndpointString = "http://localhost";
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Subject-Token","12345");
        
        AuthTokenApiService authTokenApiService = AuthTokenApiService
        .builder()
        .authUrlString(authEndpointString)
        .domain(domain)
        .username(username)
        .password(password)
        .restTemplateHttpUtil(restTemplateHttpUtil)
        .build();

        ResponseEntity<String> mockResponse = mock(ResponseEntity.class); 
        

        when(mockResponse.getBody()).thenReturn("{\n" + //
                        "    \"token\": {\n" + //
                        "        \"expires_at\": \"2024-07-18T07:19:33.273000Z\",\n" + //
                        "        \"methods\": [\n" + //
                        "            \"password\"\n" + //
                        "        ],\n" + //
                        "        \"catalog\": [],\n" + //
                        "        \"roles\": [\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_csbs_rep_acceleration\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ecs_diskAcc\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_dss_month\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_obs_deep_archive\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_cn-south-4c\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_dec_month_user\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_cbr_sellout\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ecs_old_reource\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_pangu\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_welinkbridge_endpoint_buy\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_ecp\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_cbr_file\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_dms-rocketmq5-basic\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_dms-kafka3\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_coc_ca\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_edgesec_obt\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_obs_dec_month\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_csbs_restore\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_idme_mbm_foundation\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ecs_c6a\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_fine_grained\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_multi_bind\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_smn_callnotify\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_ap-southeast-3d\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_csbs_progressbar\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ces_resourcegroup_tag\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ecs_offline_ac7\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_evs_retype\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_koomap\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_evs_essd2\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_dms-amqp-basic\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_evs_pool_ca\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_cn-southwest-2b\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_hwcph\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ecs_offline_disk_4\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_hwdev\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_op_gated_cbh_volume\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_smn_welinkred\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_cce_autopilot\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_hv_vendor\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_pro_ca\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_cn-north-4e\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_waf_cmc\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_cn-north-4d\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ecs_hecs_x\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ecs_ac7\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_csbs_restore_all\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_cn-north-4f\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_octopus\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_op_gated_roundtable\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_evs_ext\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_pfs_deep_archive\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_ap-southeast-1e\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_ru-moscow-1b\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_ap-southeast-1d\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_appstage\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_ap-southeast-1f\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_smn_application\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_evs_cold\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_rds_ca\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ecs_gpu_g5r\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_op_gated_messageover5g\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ecs_ri\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_a_ru-northwest-2c\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_gated_ief_platinum\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"0\",\n" + //
                        "                \"name\": \"op_pc_isv\"\n" + //
                        "            },\n" + //
                        "            {\n" + //
                        "                \"id\": \"8\",\n" + //
                        "                \"name\": \"37\"\n" + //
                        "            }\n" + //
                        "        ],\n" + //
                        "        \"project\": {\n" + //
                        "            \"domain\": {\n" + //
                        "                \"id\": \"0cadf620c980f2440f11c01fa8b34440\",\n" + //
                        "                \"name\": \"TestPlatform\",\n" + //
                        "                \"xdomain_id\": \"d817e230ccbc4eddb47a6dc4421372cc\",\n" + //
                        "                \"xdomain_type\": \"HWC_HK\"\n" + //
                        "            },\n" + //
                        "            \"id\": \"0d082703f700f2bc2ff9c01fc1522e58\",\n" + //
                        "            \"name\": \"ap-southeast-3\"\n" + //
                        "        },\n" + //
                        "        \"issued_at\": \"2024-07-17T07:19:33.273000Z\",\n" + //
                        "        \"user\": {\n" + //
                        "            \"domain\": {\n" + //
                        "                \"id\": \"0cadf620c980f2440f11c01fa8b34440\",\n" + //
                        "                \"name\": \"TestPlatform\",\n" + //
                        "                \"xdomain_id\": \"d817e230ccbc4eddb47a6dc4421372cc\",\n" + //
                        "                \"xdomain_type\": \"HWC_HK\"\n" + //
                        "            },\n" + //
                        "            \"id\": \"a7ef9d4b42514e69ba6e873ade9bfb1c\",\n" + //
                        "            \"name\": \"foo\",\n" + //
                        "            \"password_expires_at\": \"\"\n" + //
                        "        }\n" + //
                        "    }\n" + //
                        "}");
        when(mockResponse.getHeaders()).thenReturn(responseHeaders);
        //when(mockResponse.getStatusCode()).thenReturn(HttpStatus.OK);
        
        when( restTemplateHttpUtil.postJsonTemplate(anyString(),anyString(),anyMap()))
        .thenReturn(mockResponse);
    
        authTokenApiService.init();
        String token = authTokenApiService.getToken();
        assertTrue(StringUtils.isNoneEmpty(token));
        assertEquals(token,"12345");
    }

    
}
