# ci-chain

This Quarkus project allows you to run a CI pipeline strait from terminal, with an simple yaml configuration.

## Usage

To start using it, all you need is:
- Edit the .yaml file, specifying your CI steps;
- Past the .yaml file in your home directory;
- Run ci-chain

### Learn it by example: The yaml file

```yaml
name: codereview-pipeline
sourceInfo:
  git:
    url: "https://github.com/mrgurgel/positional-tweak.git"
    branch: master

steps:
  - name: compile
    runIn: shell
    commands:
      - mvn clean install
      - 'echo The current coverage is: 81%'
    postProcessors:
      - name: coverage
        spec:
          regexToExtractCurrentCoverageFromConsole: 'The current coverage is: (.*)%'
          minimumPercentageAccepted: 80

  - name: sonar
    disabled: true
    runIn: shell
    commands:
      - docker run --rm -e SONAR_HOST_URL="http://" -e SONAR_LOGIN="aaaaaa" sonarsource/sonar-scanner-cli

```

For the example above, the console will show:
![CI chain result](https://github.com/mrgurgel/ci-chain/blob/main/src/main/docs/output-exemple.png?raw=true)
