import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IThemeScore } from 'app/shared/model/theme-score.model';
import { ThemeScoreService } from './theme-score.service';

@Component({
  templateUrl: './theme-score-delete-dialog.component.html'
})
export class ThemeScoreDeleteDialogComponent {
  themeScore?: IThemeScore;

  constructor(
    protected themeScoreService: ThemeScoreService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.themeScoreService.delete(id).subscribe(() => {
      this.eventManager.broadcast('themeScoreListModification');
      this.activeModal.close();
    });
  }
}
