import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICuenta, Cuenta } from 'app/shared/model/cuenta.model';
import { CuentaService } from './cuenta.service';

@Component({
  selector: 'jhi-cuenta-update',
  templateUrl: './cuenta-update.component.html',
})
export class CuentaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    balance: [],
    nombre: [],
    apellido: [],
  });

  constructor(protected cuentaService: CuentaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cuenta }) => {
      this.updateForm(cuenta);
    });
  }

  updateForm(cuenta: ICuenta): void {
    this.editForm.patchValue({
      id: cuenta.id,
      balance: cuenta.balance,
      nombre: cuenta.nombre,
      apellido: cuenta.apellido,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cuenta = this.createFromForm();
    if (cuenta.id !== undefined) {
      this.subscribeToSaveResponse(this.cuentaService.update(cuenta));
    } else {
      this.subscribeToSaveResponse(this.cuentaService.create(cuenta));
    }
  }

  private createFromForm(): ICuenta {
    return {
      ...new Cuenta(),
      id: this.editForm.get(['id'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      apellido: this.editForm.get(['apellido'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICuenta>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
