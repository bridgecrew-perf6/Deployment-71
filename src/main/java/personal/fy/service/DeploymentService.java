package personal.fy.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.config.keys.ClientIdentityLoader;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.keyverifier.DefaultKnownHostsServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.apache.sshd.common.config.keys.FilePasswordProvider;
import org.apache.sshd.common.config.keys.loader.KeyPairResourceLoader;
import org.apache.sshd.common.config.keys.loader.KeyPairResourceParser;
import org.apache.sshd.common.config.keys.loader.openssh.OpenSSHKeyPairResourceParser;
import org.apache.sshd.common.util.security.SecurityUtils;
import org.apache.sshd.sftp.client.SftpClient;
import org.apache.sshd.sftp.client.SftpClientFactory;
import org.apache.sshd.sftp.server.SftpErrorStatusDataHandler;
import org.apache.sshd.sftp.server.SftpSubsystemEnvironment;
import org.apache.sshd.sftp.server.SftpSubsystemFactory;
import org.springframework.stereotype.Service;
import personal.fy.entity.DeploymentServer;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class DeploymentService {

    public void doDeployToServer(DeploymentServer server){
        SshClient sshClient = SshClient.setUpDefaultClient();
        sshClient.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
        sshClient.setClientIdentityLoader(ClientIdentityLoader.DEFAULT);
        sshClient.start();
        try(ClientSession session = sshClient.connect(server.getUser(), server.getIpaddr(), server.getSshPort())
                    .verify(Duration.ofSeconds(5))
                    .getSession()){
            session.addPasswordIdentity("*#*#8848#*#*");
            session.auth()
                    .verify(Duration.ofSeconds(5));
            SftpClientFactory sftpClientFactory = SftpClientFactory.instance();
            SftpClient sftpClient = sftpClientFactory.createSftpClient(session).singleSessionInstance();
            Iterable<SftpClient.DirEntry> dirEntries = sftpClient.readDir("/");
            dirEntries.forEach(dirEntry -> {
                log.info("dir : {}", dirEntry);
            });
            sftpClient.close();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            sshClient.stop();
        }
    }
}
