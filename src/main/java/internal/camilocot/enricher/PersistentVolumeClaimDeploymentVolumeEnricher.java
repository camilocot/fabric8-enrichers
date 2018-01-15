package internal.camilocot.enricher;

import io.fabric8.kubernetes.api.builder.TypedVisitor;
import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.KubernetesListBuilder;
import io.fabric8.kubernetes.api.model.VolumeBuilder;
import io.fabric8.kubernetes.api.model.VolumeMountBuilder;
import io.fabric8.kubernetes.api.model.PodSpecBuilder;
import io.fabric8.maven.core.util.Configs;
import io.fabric8.kubernetes.api.model.PersistentVolumeClaimVolumeSourceBuilder;
import io.fabric8.maven.enricher.api.BaseEnricher;
import io.fabric8.maven.enricher.api.EnricherContext;

public class PersistentVolumeClaimDeploymentVolumeEnricher extends BaseEnricher {

    private enum Config implements Configs.Key {
        enabled {{
            d = "yes";
        }},
        vmPath,
        vmName,
        pvcName;

        public String def() {
            return d;
        }

        protected String d;
    }

    public PersistentVolumeClaimDeploymentVolumeEnricher(EnricherContext buildContext) {
        super(buildContext, "pvc-deployment-volumemount");
    }
    
    @Override
    public void adapt(KubernetesListBuilder builder) {
        
        builder.accept(new TypedVisitor<ContainerBuilder>() {
            @Override
            public void visit(ContainerBuilder container) {
                container.addToVolumeMounts(new VolumeMountBuilder().withMountPath(getConfig(Config.vmPath)).withName(getConfig(Config.vmName)).build());
            }
        });

        builder.accept(new TypedVisitor<PodSpecBuilder>() {
            @Override
            public void visit(PodSpecBuilder pod) {
                pod.addToVolumes(0, new VolumeBuilder().withPersistentVolumeClaim(new PersistentVolumeClaimVolumeSourceBuilder().withClaimName(getConfig(Config.pvcName)).build()).withName(getConfig(Config.vmName)).build());
            }
        });
    }
    
}