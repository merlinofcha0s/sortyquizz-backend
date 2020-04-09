import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SortyquizzSharedModule } from 'app/shared/shared.module';
import { ThemeScoreComponent } from './theme-score.component';
import { ThemeScoreDetailComponent } from './theme-score-detail.component';
import { ThemeScoreUpdateComponent } from './theme-score-update.component';
import { ThemeScoreDeleteDialogComponent } from './theme-score-delete-dialog.component';
import { themeScoreRoute } from './theme-score.route';

@NgModule({
  imports: [SortyquizzSharedModule, RouterModule.forChild(themeScoreRoute)],
  declarations: [ThemeScoreComponent, ThemeScoreDetailComponent, ThemeScoreUpdateComponent, ThemeScoreDeleteDialogComponent],
  entryComponents: [ThemeScoreDeleteDialogComponent]
})
export class SortyquizzThemeScoreModule {}
