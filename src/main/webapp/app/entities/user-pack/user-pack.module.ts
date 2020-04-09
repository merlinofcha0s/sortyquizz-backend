import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SortyquizzSharedModule } from 'app/shared/shared.module';
import { UserPackComponent } from './user-pack.component';
import { UserPackDetailComponent } from './user-pack-detail.component';
import { UserPackUpdateComponent } from './user-pack-update.component';
import { UserPackDeleteDialogComponent } from './user-pack-delete-dialog.component';
import { userPackRoute } from './user-pack.route';

@NgModule({
  imports: [SortyquizzSharedModule, RouterModule.forChild(userPackRoute)],
  declarations: [UserPackComponent, UserPackDetailComponent, UserPackUpdateComponent, UserPackDeleteDialogComponent],
  entryComponents: [UserPackDeleteDialogComponent]
})
export class SortyquizzUserPackModule {}
