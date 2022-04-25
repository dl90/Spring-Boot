# Spring Boot

```bash
# Spring CLI
# --artifact-id   -a  -- Project coordinates; infer archive name (for example 'test')
# --boot-version  -b  -- Spring Boot version (for example '1.2.0.RELEASE')
# --build             -- Build system to use (for example 'maven' or 'gradle')
# --dependencies  -d  -- Comma-separated list of dependency identifiers to include in the generated project
# --description       -- Project description
# --extract       -x  -- Extract the project archive. Inferred if a location is specified without an extension
# --force         -f  -- Force overwrite of existing files
# --format            -- Format of the generated content (for example 'build' for a build file, 'project' for a project
# --group-id      -g  -- Project coordinates (for example 'org.test')
# --java-version  -j  -- Language level (for example '1.8')
# --language      -l  -- Programming language (for example 'java')
# --list              -- List the capabilities of the service. Use it to discover the dependencies and the types that ar
# --name          -n  -- Project name; infer application name
# --package-name      -- Package name
# --packaging     -p  -- Project packaging (for example 'jar')
# --target            -- URL of the service to use
# --type          -t  -- Project type. Not normally needed if you use --build and/or --format. Check the capabilities of
# --version       -v  -- Project version (for example '0.0.1-SNAPSHOT')

spring help init

spring init -d web,jpa -j 17 demoApp

# unzip to path
spring init -d web,jpa -j 17 -x ./foo/demoApp
```
