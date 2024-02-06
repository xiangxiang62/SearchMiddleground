<script setup lang="ts">
import { withDefaults, defineProps } from "vue";

interface Props {
  userList: any[];
}

const props = withDefaults(defineProps<Props>(), {
  userList: () => [],
});
</script>

<template>
  <a-list item-layout="horizontal" :data-source="userList">
    <template #renderItem="{ item }">
      <a-list-item>
        <a-card hoverable style="width: 240px">
          <template #cover>
            <img
              alt="example"
              src="https://img2.baidu.com/it/u=3385181023,3772644235&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
            />
          </template>
          <a-card-meta :title="item.userName">
            <template #description>{{ item.userProfile }}}</template>
          </a-card-meta>
        </a-card>
      </a-list-item>
    </template>
  </a-list>
</template>
<style scoped></style>
