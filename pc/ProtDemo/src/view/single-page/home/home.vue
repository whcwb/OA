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
    <div style="position: absolute;padding-top: 20px;padding-left: 20px">
      <Tooltip :content="gxsj" placement="bottom-start">
        <Button type="primary" shape="circle" icon="md-refresh" :loading="loadBtnFlag" @click="loadData"></Button>
      </Tooltip>
    </div>
    <Row :gutter="20" type="flex" justify="center" align="middle" style="padding-top: 20px">
      <Col span="6">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="primary" style="font-weight: bold;font-size: 16px">报名审核</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px" @focus="console.log(1)">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
      <Col span="6">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="primary" style="font-weight: bold;font-size: 16px">收费确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px">待收</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
          <Button type="primary" style="font-weight: bold;font-size: 16px">受理确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
      <Col span="6">
      <Card  :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px">科目一预约</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px">已约</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px">待缴</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
          <Button type="success" style="font-weight: bold;font-size: 16px">科目一成绩确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
      <Col span="6">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px">科目二预约</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px">已约</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px">待缴</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
          <Button type="success" style="font-weight: bold;font-size: 16px">科目二成绩确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="success"  ghost style="font-weight: bold;font-size: 14px">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
              <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
      <Col span="6">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="success" style="font-weight: bold;font-size: 16px">科目三预约</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px">已约</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px">待缴</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
          <Button type="success" style="font-weight: bold;font-size: 16px">科目三成绩确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
      <Col span="9">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="info" style="font-weight: bold;font-size: 16px">科目四预约</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px">已约</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
      <Col span="9">
      <Card :bordered="false" class="cardBox" style="box-shadow: 0 2px 12px rgba(0,0,0,.2)">
        <div slot="title" style="text-align: center">
          <Button type="info" style="font-weight: bold;font-size: 16px">科目四成绩确认</Button>
        </div>
        <Row style="text-align: center">
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="success"  ghost style="font-weight: bold;font-size: 14px">待办</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
            </Col>
          </Row>
          </Col>
          <Col span="2" style="text-align: center;height:80px">
          <div align="center" style="background: #e8eaec;width: 2px; height:70px;margin-left:50%;margin-top:50%;">&nbsp;</div>
          </Col>
          <Col span="10">
          <Row>
            <Col span="24">
            <Button type="error"  ghost style="font-weight: bold;font-size: 14px">异常</Button>
            </Col>
          </Row>
          <Row>
            <Col span="24">
            <count-to :end="0" count-class="count-style"/>
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
              <count-to :end="typNumlist.newborn.min" count-class="count-style"/>
              <p>小车_新生量</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(16)" icon="md-person-add" :icon-size="36">
              <count-to :end="typNumlist.newborn.max" count-class="count-style"/>
              <p>大车_新生量</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(4)" icon="md-map" :icon-size="36">
              <count-to :end="typNumlist.SL.min" count-class="count-style"/>
              <p>小车_受理量</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(5)" icon="md-map" :icon-size="36">
              <count-to :end="typNumlist.SL.max" count-class="count-style"/>
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
              <count-to :end="typNumlist.KS.one" count-class="count-style"/>
              <p>科目一考试合格人数</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(11)" icon=" iconfont icon-jineng-xiaoicon" :icon-size="36">
              <count-to :end="typNumlist.KS.two" count-class="count-style"/>
              <p>科目二考试合格人数</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(12)" icon=" iconfont icon-jiashi" :icon-size="36">
              <count-to :end="typNumlist.KS.thr" count-class="count-style"/>
              <p>科目三考试合格人数</p>
            </infor-card>
          </i-col>
        </Row>

        <Row class-name="cardSty">
          <i-col span="24" style="height: 120px;padding: 0">
            <infor-card shadow :color="col.color(17)" icon=" iconfont icon-bylx" :icon-size="36">
              <count-to :end="over" count-class="count-style"/>
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
    created() {
    },
    mounted() {
        this.gxsj = "数据更新时间:"+moment().format("YYYY-MM-DD HH:mm");
    },
    methods: {
      inputVal(val){
        // console.log('事件回调取值',val);
      },
      loadData(){
          this.loadBtnFlag = true;
          this.gxsj = "加载中";
      },
      getBM() {
        this.$http.post(this.apis.DATASTA.dateO,this.params).then((res) => {
          if (res.code == 200) {
            this.typNumlist = {
              newborn:{
                max:res.result['大车报名人数'],
                min:res.result['小车报名人数']
              },
              SL:{
                max:res.result['大车已受理学员'],
                min:res.result['小车已受理学员']
              },
              KS:{
                one:res.result['科目一合格学员'],
                two:res.result['科目二合格人数'],
                thr:res.result['科目三合格人数']
              }
            }
          }
        }).catch((err) => {

        })
      },
      getData(){
        this.$http.post(this.apis.DATASTA.dateP,this.params).then((res) => {
        if (res.code == 200 && res.result) {
          res.result.forEach((item,index)=>{
            if(item.name == '已结业人数'){
              this.over = item.value
            }
          })
        }
      }).catch((err) => {

      })
      }
    }
  }
</script>

<style lang="less">
  .count-style {
    font-size: 38px;
  }
</style>
