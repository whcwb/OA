<style lang="less">
  .cardSty{
    margin: 50px 0;
  }
  .HomeBox{
    background-image: url("./homeBack.jpg");
    background-repeat:no-repeat;
    background-size: cover;
    background-position: center;
  }
  .HomeMessTit{
    width: 100%;
    text-align: center;
    position: absolute;
    top: 15%;
    left: 38%;
    transform: translate(-50%,-50%);
    font-size: 60px;
    color: #fff;
    font-weight: 700;
  }

  .cardBox{
    .ivu-card-body{
    position: relative;
    .childerFooter{
      background-color: #EEEEEE;
      position: absolute;
      left: 0;
      right: 0;
      bottom: 0;
      height: 58px;
      }
    }
  }
</style>
<template>
  <div>
    <div style="position: absolute;padding-top: 20px;padding-left: 20px;z-index: 99">
      <Tooltip :content="gxsj" placement="bottom-start">
        <Button type="primary" shape="circle" icon="md-refresh" :loading="loadBtnFlag" @click="loadData"></Button>
      </Tooltip>
    </div>
    <div v-if="anayDivFlag" style="position: absolute;padding-top: 80px;padding-left: 20px">
      <Tooltip :content="anayGxsj" placement="bottom-start">
        <Button type="error" shape="circle" icon="md-analytics" :loading="anayBtnFlag" @click="anayData"></Button>
      </Tooltip>
    </div>
    <Row :gutter="20" type="flex" justify="center" align="middle" style="padding-top: 20px">
      <Col span="5">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="primary" style="font-weight: bold;font-size: 16px">报名审核</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPage('Student-recruit_audit')">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.BMDSH == null ? 0 : dashboard.BMDSH" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('001', null)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.BMSHYC == null ? 0 : dashboard.BMSHYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider/>
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
            &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="5">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="primary" style="font-weight: bold;font-size: 16px">收费确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPage('charge-signUp')">待收</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.SFDQR == null ? 0 : dashboard.SFDQR" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('002', null)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.SFQRYC == null ? 0 : dashboard.SFQRYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
            &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="5">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="primary" style="font-weight: bold;font-size: 16px">分期还款</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPage('charge-arrearage')">待收</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.FQDQR == null ? 0 : dashboard.FQDQR" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('903', null)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.FQQRYC == null ? 0 : dashboard.FQQRYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="5">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="primary" style="font-weight: bold;font-size: 16px">受理确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPage('Student-acceptance')">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.SLDQR == null ? 0 : dashboard.SLDQR" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('003', null)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.SLQRYC == null ? 0 : dashboard.SLQRYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
    </Row>

    <Row :gutter="20" type="flex" justify="center" align="middle" style="padding-top: 20px">
      <Col span="7">
      <Card  :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px" @click="goToPageByParam('Student-appointment', '1')">科目一预约</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('Student-query', '10')">待考</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM1YY == null ? 0 : dashboard.KM1YY" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('991', 1)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM1YYYC == null ? 0 : dashboard.KM1YYYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="6">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px">科目一初考费</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('charge-examination',1)">待缴</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM1JF == null ? 0 : dashboard.KM1JF" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('101', 1)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM1JFYC == null ? 0 : dashboard.KM1JFYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="7">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px">科目一成绩确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('Student-examination',1)">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM1DQR == null ? 0 : dashboard.KM1DQR" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('102', 1)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM1QRYC == null ? 0 : dashboard.KM1QRYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
    </Row>
    <Row :gutter="20" type="flex" justify="center" align="middle" style="padding-top: 20px">
      <Col span="7">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px" @click="goToPageByParam('Student-appointment', '2')">科目二预约</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('Student-query', '20')">待考</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM2YY == null ? 0 : dashboard.KM2YY" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('991', 2)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM2YYYC == null ? 0 : dashboard.KM2YYYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="6">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px">科目二初考费</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('charge-examination',2)">待缴</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM2JF == null ? 0 : dashboard.KM2JF" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('201', 2)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM2JFYC == null ? 0 : dashboard.KM2JFYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="7">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px">科目二成绩确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('Student-examination',2)">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM2DQR == null ? 0 : dashboard.KM2DQR" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('202', 2)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM2QRYC == null ? 0 : dashboard.KM2QRYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
    </Row>
    <Row :gutter="20" type="flex" justify="center" align="middle" style="padding-top: 20px">
      <Col span="7">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px"  @click="goToPageByParam('Student-appointment', '3')">科目三预约</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('Student-query', '30')">待考</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM3YY == null ? 0 : dashboard.KM3YY" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('991', 3)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM3YYYC == null ? 0 : dashboard.KM3YYYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="6">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px">科目三初考费</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('charge-examination',3)">待缴</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM3JF == null ? 0 : dashboard.KM3JF" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('301', 3)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM3JFYC == null ? 0 : dashboard.KM3JFYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="7">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px">科目三成绩确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('Student-examination',3)">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM3DQR == null ? 0 : dashboard.KM3DQR" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('302', 3)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM3QRYC == null ? 0 : dashboard.KM3QRYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
    </Row>
    <Row :gutter="20" type="flex" justify="center" align="middle" style="padding-top: 20px;padding-bottom: 20px">
      <Col span="10">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="info" style="font-weight: bold;font-size: 16px" @click="goToPageByParam('Student-appointment', '4')">科目四预约</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('Student-query', '40')">待考</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM4YY == null ? 0 : dashboard.KM4YY" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('991', 4)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM4YYYC == null ? 0 : dashboard.KM4YYYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
      <Col span="10">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="info" style="font-weight: bold;font-size: 16px">科目四成绩确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @click="goToPageByParam('Student-examination',4)">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM4DQR == null ? 0 : dashboard.KM4DQR" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px" @click="goToExceptionPage('402', 4)">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="dashboard.KM4QRYC == null ? 0 : dashboard.KM4QRYC" :duration='0.2' count-class="count-style-excpetion"/>
            </Col>
          </Row>
          </Col>
        </Row>
        <Divider />
        <Row class="childerFooter" type="flex" justify="center" align="middle">
          <Col offset="1" span="22">
          &nbsp;
          </Col>
        </Row>
      </Card>
      </Col>
    </Row>
  </div>

  <!--<div class="HomeBox" style="position: relative;min-height: 100%">
    <Row :gutter="20" v-if="false">
      <i-col span="4" v-if="typNumlist.newborn">

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(1)" icon="md-person-add" :icon-size="36">
              <count-to :end="typNumlist.newborn.min" count-class="count-style-excpetion"/>
              <p>小车_新生量</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(16)" icon="md-person-add" :icon-size="36">
              <count-to :end="typNumlist.newborn.max" count-class="count-style-excpetion"/>
              <p>大车_新生量</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(4)" icon="md-map" :icon-size="36">
              <count-to :end="typNumlist.SL.min" count-class="count-style-excpetion"/>
              <p>小车_受理量</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(5)" icon="md-map" :icon-size="36">
              <count-to :end="typNumlist.SL.max" count-class="count-style-excpetion"/>
              <p>大车_受理量</p>
            </infor-card>
          </i-col>
        </Row>

      </i-col>

      <i-col span="16">
        <Card shadow>
          <chart-pie :time="params" :style="{height:AF.getPageHeight()-300+'px'}"></chart-pie>
        </Card>
      </i-col>

      <i-col span="4" style="height: 120px;" v-if="typNumlist.KS">

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(10)" icon=" iconfont icon-lilun" :icon-size="36">
              <count-to :end="typNumlist.KS.one" count-class="count-style-excpetion"/>
              <p>科目一考试合格人数</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(11)" icon=" iconfont icon-jineng-xiaoicon" :icon-size="36">
              <count-to :end="typNumlist.KS.two" count-class="count-style-excpetion"/>
              <p>科目二考试合格人数</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(12)" icon=" iconfont icon-jiashi" :icon-size="36">
              <count-to :end="typNumlist.KS.thr" count-class="count-style-excpetion"/>
              <p>科目三考试合格人数</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(17)" icon=" iconfont icon-bylx" :icon-size="36">
              <count-to :end="over" count-class="count-style-excpetion"/>
              <p>毕业人数</p>
            </infor-card>
          </i-col>
        </Row>

      </i-col>
    </Row>

  </div>-->
</template>

<script>
  import InforCard from '_c/info-card'
  import CountTo from '_c/count-to'
  import {ChartBar, ChartPie} from '_c/charts'
  import Example from './example.vue'
  import moment from 'moment'

  // import RadioCar from '../../lcsf/comp/RadioCar'

  // import sel from '../../../components/select'
  export default {
    name: 'home',
    components: {
      InforCard,
      CountTo,
      ChartPie,
      ChartBar,
      Example,
      // RadioCar,
      // sel
    },
    data() {
      return {
        typNumlist: {},
        over:0,
        gxsj:'',
        loadBtnFlag:false,
        anayBtnFlag:false,
        anayDivFlag:false,
        anayGxsj:'异常统计',
        dashboard:{},
        params:{
          startTime:'',
          endTime:this.AF.trimDate()
        },
        option: [
          {key: 1, val: 1},
          {key: 2, val: 2},
          {key: 3, val: 3},
          {key: 4, val: 4},
          {key: 5, val: 5},
          {key: 6, val: 6},
          {key: 7, val: 7},
          {key: 8, val: 8},
          {key: 9, val: 9},
          {key: 10, val: 10},
        ]
      }
    },
    mounted() {
        this.loadData();
        //
        var userInfo = sessionStorage.getItem("userInfo");
        var uData = JSON.parse(userInfo);
        if (uData.type == "su"){
          this.anayDivFlag = true;
        }else{
          this.anayDivFlag = false;
        }
    },
    methods: {
      inputVal(val){
        // console.log('事件回调取值',val);
      },
      loadData(){
          this.loadBtnFlag = true;
          this.gxsj = "加载中";
          this.$http.get(this.apis.HOME.DASHBOARD).then((res) => {
              this.loadBtnFlag = false;
              if (res.code == 200) {
                this.gxsj = "数据更新时间:"+moment().format("YYYY-MM-DD HH:mm");
                this.dashboard = res.result;
              }else{
                this.gxsj = "数据更新失败";
              }
          }).catch((err) => {
            this.loadBtnFlag = false;
            this.gxsj = "数据加载失败";
          })
      },
      anayData(){
        this.anayBtnFlag = true;
        this.anayGxsj = "加载中";
        this.$http.post(this.apis.HOME.RUNEXCEPTION).then((res) => {
          this.anayBtnFlag = false;
          if (res.code == 200) {
            this.anayGxsj = "数据更新时间:"+moment().format("YYYY-MM-DD HH:mm");
            this.loadData();
          }else{
            this.anayGxsj = "数据更新失败";
          }
        }).catch((err) => {
          this.anayBtnFlag = false;
          this.anayGxsj = "数据加载失败";
        })
      },
      goToPage(pageName){
          var permission = JSON.stringify(this.$store.state.app.permissionMenuList);
          if (permission.indexOf(pageName) != -1){
              this.$router.push({ name: pageName });
          }
      },
      goToPageByParam(pageName, param){
        var permission = JSON.stringify(this.$store.state.app.permissionMenuList);
        if (permission.indexOf(pageName) != -1){
          this.$router.push({ name: pageName, query:{param: param} });
        }
      },
      goToExceptionPage(code ,kskm){
          this.$router.push({ name: 'Student-exception', query:{code: code, kskm:kskm} });
      }
    }
  }
</script>

<style lang="less">
  .count-style-excpetion {
    font-size: 26px;
  }
</style>
