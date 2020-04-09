import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IUserPack, UserPack } from 'app/shared/model/user-pack.model';
import { UserPackService } from './user-pack.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile/profile.service';
import { IPack } from 'app/shared/model/pack.model';
import { PackService } from 'app/entities/pack/pack.service';

type SelectableEntity = IProfile | IPack;

@Component({
  selector: 'jhi-user-pack-update',
  templateUrl: './user-pack-update.component.html'
})
export class UserPackUpdateComponent implements OnInit {
  isSaving = false;
  profiles: IProfile[] = [];
  packs: IPack[] = [];

  editForm = this.fb.group({
    id: [],
    state: [null, [Validators.required]],
    lifeLeft: [null, [Validators.required]],
    nbQuestionsToSucceed: [null, [Validators.required]],
    timeAtQuizzStep: [null, [Validators.required]],
    timeAtSortingStep: [null, [Validators.required]],
    profileId: [],
    packId: []
  });

  constructor(
    protected userPackService: UserPackService,
    protected profileService: ProfileService,
    protected packService: PackService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ userPack }) => {
      this.updateForm(userPack);

      this.profileService.query().subscribe((res: HttpResponse<IProfile[]>) => (this.profiles = res.body || []));

      this.packService.query().subscribe((res: HttpResponse<IPack[]>) => (this.packs = res.body || []));
    });
  }

  updateForm(userPack: IUserPack): void {
    this.editForm.patchValue({
      id: userPack.id,
      state: userPack.state,
      lifeLeft: userPack.lifeLeft,
      nbQuestionsToSucceed: userPack.nbQuestionsToSucceed,
      timeAtQuizzStep: userPack.timeAtQuizzStep,
      timeAtSortingStep: userPack.timeAtSortingStep,
      profileId: userPack.profileId,
      packId: userPack.packId
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const userPack = this.createFromForm();
    if (userPack.id !== undefined) {
      this.subscribeToSaveResponse(this.userPackService.update(userPack));
    } else {
      this.subscribeToSaveResponse(this.userPackService.create(userPack));
    }
  }

  private createFromForm(): IUserPack {
    return {
      ...new UserPack(),
      id: this.editForm.get(['id'])!.value,
      state: this.editForm.get(['state'])!.value,
      lifeLeft: this.editForm.get(['lifeLeft'])!.value,
      nbQuestionsToSucceed: this.editForm.get(['nbQuestionsToSucceed'])!.value,
      timeAtQuizzStep: this.editForm.get(['timeAtQuizzStep'])!.value,
      timeAtSortingStep: this.editForm.get(['timeAtSortingStep'])!.value,
      profileId: this.editForm.get(['profileId'])!.value,
      packId: this.editForm.get(['packId'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IUserPack>>): void {
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
