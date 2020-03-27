import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPack, Pack } from 'app/shared/model/pack.model';
import { PackService } from './pack.service';

@Component({
  selector: 'jhi-pack-update',
  templateUrl: './pack-update.component.html'
})
export class PackUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    category: [null, [Validators.required]]
  });

  constructor(protected packService: PackService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pack }) => {
      this.updateForm(pack);
    });
  }

  updateForm(pack: IPack): void {
    this.editForm.patchValue({
      id: pack.id,
      name: pack.name,
      category: pack.category
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pack = this.createFromForm();
    if (pack.id !== undefined) {
      this.subscribeToSaveResponse(this.packService.update(pack));
    } else {
      this.subscribeToSaveResponse(this.packService.create(pack));
    }
  }

  private createFromForm(): IPack {
    return {
      ...new Pack(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      category: this.editForm.get(['category'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPack>>): void {
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
