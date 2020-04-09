import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICard, Card } from 'app/shared/model/card.model';
import { CardService } from './card.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IPack } from 'app/shared/model/pack.model';
import { PackService } from 'app/entities/pack/pack.service';

@Component({
  selector: 'jhi-card-update',
  templateUrl: './card-update.component.html'
})
export class CardUpdateComponent implements OnInit {
  isSaving = false;
  packs: IPack[] = [];

  editForm = this.fb.group({
    id: [],
    display: [null, [Validators.required]],
    valueToSort: [null, [Validators.required]],
    valueType: [null, [Validators.required]],
    picture: [null, [Validators.required]],
    pictureContentType: [],
    sortingType: [null, [Validators.required]],
    order: [],
    packId: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected cardService: CardService,
    protected packService: PackService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ card }) => {
      this.updateForm(card);

      this.packService.query().subscribe((res: HttpResponse<IPack[]>) => (this.packs = res.body || []));
    });
  }

  updateForm(card: ICard): void {
    this.editForm.patchValue({
      id: card.id,
      display: card.display,
      valueToSort: card.valueToSort,
      valueType: card.valueType,
      picture: card.picture,
      pictureContentType: card.pictureContentType,
      sortingType: card.sortingType,
      order: card.order,
      packId: card.packId
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('sortyquizzApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const card = this.createFromForm();
    if (card.id !== undefined) {
      this.subscribeToSaveResponse(this.cardService.update(card));
    } else {
      this.subscribeToSaveResponse(this.cardService.create(card));
    }
  }

  private createFromForm(): ICard {
    return {
      ...new Card(),
      id: this.editForm.get(['id'])!.value,
      display: this.editForm.get(['display'])!.value,
      valueToSort: this.editForm.get(['valueToSort'])!.value,
      valueType: this.editForm.get(['valueType'])!.value,
      pictureContentType: this.editForm.get(['pictureContentType'])!.value,
      picture: this.editForm.get(['picture'])!.value,
      sortingType: this.editForm.get(['sortingType'])!.value,
      order: this.editForm.get(['order'])!.value,
      packId: this.editForm.get(['packId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICard>>): void {
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

  trackById(index: number, item: IPack): any {
    return item.id;
  }
}
