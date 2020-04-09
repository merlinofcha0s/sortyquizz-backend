import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IThemeScore } from 'app/shared/model/theme-score.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ThemeScoreService } from './theme-score.service';
import { ThemeScoreDeleteDialogComponent } from './theme-score-delete-dialog.component';

@Component({
  selector: 'jhi-theme-score',
  templateUrl: './theme-score.component.html'
})
export class ThemeScoreComponent implements OnInit, OnDestroy {
  themeScores: IThemeScore[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected themeScoreService: ThemeScoreService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.themeScores = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.themeScoreService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IThemeScore[]>) => this.paginateThemeScores(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.themeScores = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInThemeScores();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IThemeScore): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInThemeScores(): void {
    this.eventSubscriber = this.eventManager.subscribe('themeScoreListModification', () => this.reset());
  }

  delete(themeScore: IThemeScore): void {
    const modalRef = this.modalService.open(ThemeScoreDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.themeScore = themeScore;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateThemeScores(data: IThemeScore[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.themeScores.push(data[i]);
      }
    }
  }
}
