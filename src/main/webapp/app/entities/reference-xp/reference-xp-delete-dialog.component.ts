import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReferenceXP } from 'app/shared/model/reference-xp.model';
import { ReferenceXPService } from './reference-xp.service';

@Component({
  templateUrl: './reference-xp-delete-dialog.component.html'
})
export class ReferenceXPDeleteDialogComponent {
  referenceXP?: IReferenceXP;

  constructor(
    protected referenceXPService: ReferenceXPService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.referenceXPService.delete(id).subscribe(() => {
      this.eventManager.broadcast('referenceXPListModification');
      this.activeModal.close();
    });
  }
}
