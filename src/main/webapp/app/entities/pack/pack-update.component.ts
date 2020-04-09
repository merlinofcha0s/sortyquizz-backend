import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPack, Pack } from 'app/shared/model/pack.model';
import { PackService } from './pack.service';
import { IRule } from 'app/shared/model/rule.model';
import { RuleService } from 'app/entities/rule/rule.service';
import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from 'app/entities/theme/theme.service';

type SelectableEntity = IRule | ITheme;

@Component({
  selector: 'jhi-pack-update',
  templateUrl: './pack-update.component.html'
})
export class PackUpdateComponent implements OnInit {
  isSaving = false;
  rules: IRule[] = [];
  themes: ITheme[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    level: [null, [Validators.required]],
    type: [null, [Validators.required]],
    life: [null, [Validators.required]],
    ruleId: [],
    themeId: []
  });

  constructor(
    protected packService: PackService,
    protected ruleService: RuleService,
    protected themeService: ThemeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pack }) => {
      this.updateForm(pack);

      this.ruleService
        .query({ filter: 'pack-is-null' })
        .pipe(
          map((res: HttpResponse<IRule[]>) => {
            return res.body || [];
          })
        )
        .subscribe((resBody: IRule[]) => {
          if (!pack.ruleId) {
            this.rules = resBody;
          } else {
            this.ruleService
              .find(pack.ruleId)
              .pipe(
                map((subRes: HttpResponse<IRule>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IRule[]) => (this.rules = concatRes));
          }
        });

      this.themeService.query().subscribe((res: HttpResponse<ITheme[]>) => (this.themes = res.body || []));
    });
  }

  updateForm(pack: IPack): void {
    this.editForm.patchValue({
      id: pack.id,
      name: pack.name,
      level: pack.level,
      type: pack.type,
      life: pack.life,
      ruleId: pack.ruleId,
      themeId: pack.themeId
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
      level: this.editForm.get(['level'])!.value,
      type: this.editForm.get(['type'])!.value,
      life: this.editForm.get(['life'])!.value,
      ruleId: this.editForm.get(['ruleId'])!.value,
      themeId: this.editForm.get(['themeId'])!.value
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
