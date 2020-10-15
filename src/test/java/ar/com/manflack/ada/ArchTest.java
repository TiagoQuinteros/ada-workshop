package ar.com.manflack.ada;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ar.com.manflack.ada");

        noClasses()
            .that()
            .resideInAnyPackage("ar.com.manflack.ada.service..")
            .or()
            .resideInAnyPackage("ar.com.manflack.ada.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..ar.com.manflack.ada.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
