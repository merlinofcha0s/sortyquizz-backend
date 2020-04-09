import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IThemeScore, ThemeScore } from 'app/shared/model/theme-score.model';
import { ThemeScoreService } from './theme-score.service';
import { ThemeScoreComponent } from './theme-score.component';
import { ThemeScoreDetailComponent } from './theme-score-detail.component';
import { ThemeScoreUpdateComponent } from './theme-score-update.component';

@Injectable({ providedIn: 'root' })
export class ThemeScoreResolve implements Resolve<IThemeScore> {
  constructor(private service: ThemeScoreService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IThemeScore> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((themeScore: HttpResponse<ThemeScore>) => {
          if (themeScore.body) {
            return of(themeScore.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ThemeScore());
  }
}

export const themeScoreRoute: Routes = [
  {
    path: '',
    component: ThemeScoreComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.themeScore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ThemeScoreDetailComponent,
    resolve: {
      themeScore: ThemeScoreResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.themeScore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ThemeScoreUpdateComponent,
    resolve: {
      themeScore: ThemeScoreResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.themeScore.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ThemeScoreUpdateComponent,
    resolve: {
      themeScore: ThemeScoreResolve
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'sortyquizzApp.themeScore.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
