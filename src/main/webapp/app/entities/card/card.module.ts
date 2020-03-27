import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SortyquizzSharedModule } from 'app/shared/shared.module';
import { CardComponent } from './card.component';
import { CardDetailComponent } from './card-detail.component';
import { CardUpdateComponent } from './card-update.component';
import { CardDeleteDialogComponent } from './card-delete-dialog.component';
import { cardRoute } from './card.route';

@NgModule({
  imports: [SortyquizzSharedModule, RouterModule.forChild(cardRoute)],
  declarations: [CardComponent, CardDetailComponent, CardUpdateComponent, CardDeleteDialogComponent],
  entryComponents: [CardDeleteDialogComponent]
})
export class SortyquizzCardModule {}
