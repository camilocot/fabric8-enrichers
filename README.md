# fabric8 Maven Plugin PVC Enricher

This fabric8 maven plugin enricher allows enriching the kubernetes deployments with PVC and VolumeMounts

## Build

Clone the repo https://github.com/kameshsampath/fmp-istio-enricher and run `mvn clean install` from $PROJECT_HOME

## Usage

Create https://maven.fabric8.io/#profiles[fabric8 maven plugin profile] called *profiles.yml* inside `$basedir/src/main/fabric8`.

The following example shows a profile which can be used in conjunction with spring-boot app. Please refer to https://maven.fabric8.io/#profiles[fabric8 maven plugin profile] for more details

```
----
- name: pvc-deployment-volumemount
  enricher:
    # The order given in "includes" is the order in which enrichers are called
    includes:
    - fmp-name
    - fmp-controller
    - fmp-service
    - fmp-image
    - fmp-portname
    - fmp-project
    - fmp-pod-annotations
    - fmp-debug
    - fmp-merge
    - fmp-remove-build-annotations
    - fmp-volume-permission
    - pvc-deployment-volumemount
    - f8-expose
    # Health checks
    - spring-boot-health-check
    - docker-health-check
    - fmp-dependency
    - f8-watch
  generator:
    # The order given in "includes" is the order in which generators are called
    includes:
    - spring-boot
  watcher:
    includes:
    - spring-boot
    - docker-image
```