import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IRule } from 'app/shared/model/rule.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { RuleService } from './rule.service';
import { RuleDeleteDialogComponent } from './rule-delete-dialog.component';

@Component({
  selector: 'jhi-rule',
  templateUrl: './rule.component.html'
})
export class RuleComponent implements OnInit, OnDestroy {
  rules: IRule[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected ruleService: RuleService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.rules = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.ruleService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<IRule[]>) => this.paginateRules(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.rules = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInRules();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IRule): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInRules(): void {
    this.eventSubscriber = this.eventManager.subscribe('ruleListModification', () => this.reset());
  }

  delete(rule: IRule): void {
    const modalRef = this.modalService.open(RuleDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.rule = rule;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateRules(data: IRule[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.rules.push(data[i]);
      }
    }
  }
}
