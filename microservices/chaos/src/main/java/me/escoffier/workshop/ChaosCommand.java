package me.escoffier.workshop;

import io.fabric8.kubernetes.api.model.OwnerReference;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.jboss.logging.Logger;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import java.util.Random;


import javax.inject.Inject;
import java.util.List;

@Command(name = "chaos", mixinStandardHelpOptions = true)
public class ChaosCommand implements Runnable {

    @CommandLine.Option(
        names = {"--deployment", "-d"}, required = true, description = "The targeted deployment")
    String deployment;

    @Inject KubernetesClient kubernetes;
    @Inject Logger logger;

    @Override
    public void run() {
        logger.infof("Connected to %s, current namespace is %s", kubernetes.getMasterUrl(), kubernetes.getNamespace());

        List<Pod> pods = kubernetes.pods().list().getItems();
        for (Pod pod : pods) {
            logger.infof("Pod %s - %s", pod.getMetadata().getName(), pod.getStatus().getPhase());
            for (OwnerReference reference : pod.getMetadata().getOwnerReferences()) {
                logger.infof("\t Owner: %s (%s)", reference.getName(), reference.getKind());
            }
            
			if((pod.getMetadata().getName().contains(deployment) && !pod.getMetadata().getName().contains("postgres")) && new Random().nextInt(3) == 0){
				kubernetes.pods().delete(pod);
				logger.infof("Pod %s - %s (deleted)", pod.getMetadata().getName(), pod.getStatus().getPhase());
			}
        }

        List<Deployment> deployments = kubernetes.apps().deployments().list().getItems();
        for (Deployment dep : deployments) {
            logger.infof("Deployment %s", dep.getMetadata().getName());
        }
    }

}
