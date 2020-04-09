import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IRule, Rule } from 'app/shared/model/rule.model';
import { RuleService } from './rule.service';

@Component({
  selector: 'jhi-rule-update',
  templateUrl: './rule-update.component.html'
})
export class RuleUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nbMaxQuestions: [null, [Validators.required]],
    timePerQuestion: [null, [Validators.required]],
    timeForSorting: [null, [Validators.required]],
    nbMinCardToWin: [null, [Validators.required]]
  });

  constructor(protected ruleService: RuleService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ rule }) => {
      this.updateForm(rule);
    });
  }

  updateForm(rule: IRule): void {
    this.editForm.patchValue({
      id: rule.id,
      nbMaxQuestions: rule.nbMaxQuestions,
      timePerQuestion: rule.timePerQuestion,
      timeForSorting: rule.timeForSorting,
      nbMinCardToWin: rule.nbMinCardToWin
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const rule = this.createFromForm();
    if (rule.id !== undefined) {
      this.subscribeToSaveResponse(this.ruleService.update(rule));
    } else {
      this.subscribeToSaveResponse(this.ruleService.create(rule));
    }
  }

  private createFromForm(): IRule {
    return {
      ...new Rule(),
      id: this.editForm.get(['id'])!.value,
      nbMaxQuestions: this.editForm.get(['nbMaxQuestions'])!.value,
      timePerQuestion: this.editForm.get(['timePerQuestion'])!.value,
      timeForSorting: this.editForm.get(['timeForSorting'])!.value,
      nbMinCardToWin: this.editForm.get(['nbMinCardToWin'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IRule>>): void {
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
