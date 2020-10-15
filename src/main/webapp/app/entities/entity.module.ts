import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'cuenta',
        loadChildren: () => import('./cuenta/cuenta.module').then(m => m.AdaWorkshopCuentaModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class AdaWorkshopEntityModule {}
