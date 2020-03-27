import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.SortyquizzProfileModule)
      },
      {
        path: 'card',
        loadChildren: () => import('./card/card.module').then(m => m.SortyquizzCardModule)
      },
      {
        path: 'pack',
        loadChildren: () => import('./pack/pack.module').then(m => m.SortyquizzPackModule)
      },
      {
        path: 'question',
        loadChildren: () => import('./question/question.module').then(m => m.SortyquizzQuestionModule)
      },
      {
        path: 'answer',
        loadChildren: () => import('./answer/answer.module').then(m => m.SortyquizzAnswerModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class SortyquizzEntityModule {}
