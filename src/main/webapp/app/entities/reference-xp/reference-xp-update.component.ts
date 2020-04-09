import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IReferenceXP, ReferenceXP } from 'app/shared/model/reference-xp.model';
import { ReferenceXPService } from './reference-xp.service';

@Component({
  selector: 'jhi-reference-xp-update',
  templateUrl: './reference-xp-update.component.html'
})
export class ReferenceXPUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    level: [null, [Validators.required]],
    minXp: [null, [Validators.required]],
    maxXp: [null, [Validators.required]],
    xpType: [null, [Validators.required]]
  });

  constructor(protected referenceXPService: ReferenceXPService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ referenceXP }) => {
      this.updateForm(referenceXP);
    });
  }

  updateForm(referenceXP: IReferenceXP): void {
    this.editForm.patchValue({
      id: referenceXP.id,
      level: referenceXP.level,
      minXp: referenceXP.minXp,
      maxXp: referenceXP.maxXp,
      xpType: referenceXP.xpType
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const referenceXP = this.createFromForm();
    if (referenceXP.id !== undefined) {
      this.subscribeToSaveResponse(this.referenceXPService.update(referenceXP));
    } else {
      this.subscribeToSaveResponse(this.referenceXPService.create(referenceXP));
    }
  }

  private createFromForm(): IReferenceXP {
    return {
      ...new ReferenceXP(),
      id: this.editForm.get(['id'])!.value,
      level: this.editForm.get(['level'])!.value,
      minXp: this.editForm.get(['minXp'])!.value,
      maxXp: this.editForm.get(['maxXp'])!.value,
      xpType: this.editForm.get(['xpType'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReferenceXP>>): void {
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
