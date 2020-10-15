import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AdaWorkshopSharedModule } from 'app/shared/shared.module';
import { CuentaComponent } from './cuenta.component';
import { CuentaDetailComponent } from './cuenta-detail.component';
import { CuentaUpdateComponent } from './cuenta-update.component';
import { CuentaDeleteDialogComponent } from './cuenta-delete-dialog.component';
import { cuentaRoute } from './cuenta.route';

@NgModule({
  imports: [AdaWorkshopSharedModule, RouterModule.forChild(cuentaRoute)],
  declarations: [CuentaComponent, CuentaDetailComponent, CuentaUpdateComponent, CuentaDeleteDialogComponent],
  entryComponents: [CuentaDeleteDialogComponent],
})
export class AdaWorkshopCuentaModule {}
