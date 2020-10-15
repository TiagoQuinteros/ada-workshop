import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from './cuenta.service';
import { CuentaDeleteDialogComponent } from './cuenta-delete-dialog.component';

@Component({
  selector: 'jhi-cuenta',
  templateUrl: './cuenta.component.html',
})
export class CuentaComponent implements OnInit, OnDestroy {
  cuentas?: ICuenta[];
  eventSubscriber?: Subscription;

  constructor(protected cuentaService: CuentaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.cuentaService.query().subscribe((res: HttpResponse<ICuenta[]>) => (this.cuentas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCuentas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICuenta): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCuentas(): void {
    this.eventSubscriber = this.eventManager.subscribe('cuentaListModification', () => this.loadAll());
  }

  delete(cuenta: ICuenta): void {
    const modalRef = this.modalService.open(CuentaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.cuenta = cuenta;
  }
}
