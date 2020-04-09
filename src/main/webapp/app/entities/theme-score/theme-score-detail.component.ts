import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IThemeScore } from 'app/shared/model/theme-score.model';

@Component({
  selector: 'jhi-theme-score-detail',
  templateUrl: './theme-score-detail.component.html'
})
export class ThemeScoreDetailComponent implements OnInit {
  themeScore: IThemeScore | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ themeScore }) => (this.themeScore = themeScore));
  }

  previousState(): void {
    window.history.back();
  }
}
