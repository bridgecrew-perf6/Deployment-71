package personal.fy.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import personal.fy.entity.DeploymentServer;

import java.io.File;
import java.util.Map;

@Slf4j
public class DeploymentServiceTests {

    DeploymentService deploymentService = new DeploymentService();

    @Test
    void doDeployToServer() {
        DeploymentServer deploymentServer = new DeploymentServer();
        deploymentServer.setIpaddr("192.168.0.110");
        deploymentServer.setUser("root");
        deploymentServer.setPassword("*#*#8848#*#*");
        deploymentServer.setSshPort(22);
        deploymentService.doDeployToServer(deploymentServer);
    }

    @Test
    void systemVaribales() {
        log.info("home dir: {}", System.getProperty("user.home"));
    }
}
