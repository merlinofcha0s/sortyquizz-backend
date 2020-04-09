import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SortyquizzSharedModule } from 'app/shared/shared.module';
import { ReferenceXPComponent } from './reference-xp.component';
import { ReferenceXPDetailComponent } from './reference-xp-detail.component';
import { ReferenceXPUpdateComponent } from './reference-xp-update.component';
import { ReferenceXPDeleteDialogComponent } from './reference-xp-delete-dialog.component';
import { referenceXPRoute } from './reference-xp.route';

@NgModule({
  imports: [SortyquizzSharedModule, RouterModule.forChild(referenceXPRoute)],
  declarations: [ReferenceXPComponent, ReferenceXPDetailComponent, ReferenceXPUpdateComponent, ReferenceXPDeleteDialogComponent],
  entryComponents: [ReferenceXPDeleteDialogComponent]
})
export class SortyquizzReferenceXPModule {}
