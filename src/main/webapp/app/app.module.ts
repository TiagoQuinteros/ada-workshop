import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AdaWorkshopSharedModule } from 'app/shared/shared.module';
import { AdaWorkshopCoreModule } from 'app/core/core.module';
import { AdaWorkshopAppRoutingModule } from './app-routing.module';
import { AdaWorkshopHomeModule } from './home/home.module';
import { AdaWorkshopEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AdaWorkshopSharedModule,
    AdaWorkshopCoreModule,
    AdaWorkshopHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AdaWorkshopEntityModule,
    AdaWorkshopAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, FooterComponent],
  bootstrap: [MainComponent],
})
export class AdaWorkshopAppModule {}
