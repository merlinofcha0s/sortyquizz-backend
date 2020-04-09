import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IUserPack } from 'app/shared/model/user-pack.model';
import { UserPackService } from './user-pack.service';

@Component({
  templateUrl: './user-pack-delete-dialog.component.html'
})
export class UserPackDeleteDialogComponent {
  userPack?: IUserPack;

  constructor(protected userPackService: UserPackService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.userPackService.delete(id).subscribe(() => {
      this.eventManager.broadcast('userPackListModification');
      this.activeModal.close();
    });
  }
}
