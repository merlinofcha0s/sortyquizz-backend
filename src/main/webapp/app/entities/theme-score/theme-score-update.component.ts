import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IThemeScore, ThemeScore } from 'app/shared/model/theme-score.model';
import { ThemeScoreService } from './theme-score.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile/profile.service';
import { ITheme } from 'app/shared/model/theme.model';
import { ThemeService } from 'app/entities/theme/theme.service';

type SelectableEntity = IProfile | ITheme;

@Component({
  selector: 'jhi-theme-score-update',
  templateUrl: './theme-score-update.component.html'
})
export class ThemeScoreUpdateComponent implements OnInit {
  isSaving = false;
  profiles: IProfile[] = [];
  themes: ITheme[] = [];

  editForm = this.fb.group({
    id: [],
    xp: [null, [Validators.required]],
    level: [null, [Validators.required]],
    profileId: [],
    themeId: []
  });

  constructor(
    protected themeScoreService: ThemeScoreService,
    protected profileService: ProfileService,
    protected themeService: ThemeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ themeScore }) => {
      this.updateForm(themeScore);

      this.profileService.query().subscribe((res: HttpResponse<IProfile[]>) => (this.profiles = res.body || []));

      this.themeService.query().subscribe((res: HttpResponse<ITheme[]>) => (this.themes = res.body || []));
    });
  }

  updateForm(themeScore: IThemeScore): void {
    this.editForm.patchValue({
      id: themeScore.id,
      xp: themeScore.xp,
      level: themeScore.level,
      profileId: themeScore.profileId,
      themeId: themeScore.themeId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const themeScore = this.createFromForm();
    if (themeScore.id !== undefined) {
      this.subscribeToSaveResponse(this.themeScoreService.update(themeScore));
    } else {
      this.subscribeToSaveResponse(this.themeScoreService.create(themeScore));
    }
  }

  private createFromForm(): IThemeScore {
    return {
      ...new ThemeScore(),
      id: this.editForm.get(['id'])!.value,
      xp: this.editForm.get(['xp'])!.value,
      level: this.editForm.get(['level'])!.value,
      profileId: this.editForm.get(['profileId'])!.value,
      themeId: this.editForm.get(['themeId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IThemeScore>>): void {
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
