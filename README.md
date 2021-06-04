# ciriema

This Quarkus project allows you to run a CI pipeline strait from terminal, with an simple yaml configuration.

## Usage

To start using it, all you need is:
- Edit the .yaml file, specifying your CI steps;
- Past the .yaml file in your home directory;
- Run ciriema

### Learn it by example: The yaml file

```yaml
name: codereview-pipeline
sourceInfo:
  git:
    url: "https://github.com/ttzoutz/jenkin-devops-microservice.git" # Random project that I picked-up from the internet
    branch: master

steps:
  - name: compile
    runIn: shell
    commands:
      - mvn package
      - java -jar /Users/mrgurgel/apps/jacoco-0.8.7/lib/jacococli.jar report target/jacoco.exec --classfiles ./target/classes --html /tmp/jacoco-report
      - cat /tmp/jacoco-report/index.html
    postProcessors:
      - name: coverage
        spec:
          regexToExtractCurrentCoverageFromConsole: 'title="1" alt="1"\/><\/td><td class="ctr2" id="e1">(.*)%<\/td>'
          minimumPercentageAccepted: 80
```

For the example above, the console will show:
![Ciriema result](https://github.com/mrgurgel/ciriema/blob/main/src/main/docs/output-exemple.png?raw=true)
